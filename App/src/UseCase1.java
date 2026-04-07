import java.util.*;
import java.util.concurrent.*;

// Custom exception for invalid bookings
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private int numberOfRooms;

    public Reservation(String reservationId, String guestName, String roomType, int numberOfRooms) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.numberOfRooms = numberOfRooms;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public int getNumberOfRooms() { return numberOfRooms; }

    @Override
    public String toString() {
        return "Reservation{" +
                "ID='" + reservationId + '\'' +
                ", Guest='" + guestName + '\'' +
                ", RoomType='" + roomType + '\'' +
                ", Rooms=" + numberOfRooms +
                '}';
    }
}

// Thread-safe Inventory Manager
class InventoryManager {
    private final Map<String, Integer> roomInventory = new HashMap<>();
    private final Map<String, Reservation> confirmedReservations = new ConcurrentHashMap<>();

    public InventoryManager() {
        roomInventory.put("Single", 5);
        roomInventory.put("Double", 5);
        roomInventory.put("Suite", 2);
    }

    // Synchronized booking to ensure thread safety
    public synchronized void bookRoom(Reservation res) throws InvalidBookingException {
        String type = res.getRoomType();
        int requested = res.getNumberOfRooms();

        if (!roomInventory.containsKey(type)) {
            throw new InvalidBookingException("Invalid room type: " + type);
        }
        if (requested <= 0) {
            throw new InvalidBookingException("Number of rooms must be positive.");
        }

        int available = roomInventory.get(type);
        if (requested > available) {
            throw new InvalidBookingException("Not enough rooms available. Requested: " + requested + ", Available: " + available);
        }

        roomInventory.put(type, available - requested);
        confirmedReservations.put(res.getReservationId(), res);
        System.out.println(Thread.currentThread().getName() + " booked: " + res);
    }

    public synchronized void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        roomInventory.forEach((type, count) -> System.out.println(type + ": " + count + " rooms available"));
    }

    public void displayConfirmedBookings() {
        if (confirmedReservations.isEmpty()) {
            System.out.println("No confirmed bookings.");
            return;
        }
        System.out.println("\nConfirmed Bookings:");
        confirmedReservations.values().forEach(System.out::println);
    }
}

// Booking Task for threads
class BookingTask implements Runnable {
    private final InventoryManager manager;
    private final Reservation reservation;

    public BookingTask(InventoryManager manager, Reservation reservation) {
        this.manager = manager;
        this.reservation = reservation;
    }

    @Override
    public void run() {
        try {
            manager.bookRoom(reservation);
        } catch (InvalidBookingException e) {
            System.out.println(Thread.currentThread().getName() + " booking failed: " + e.getMessage());
        }
    }
}

// Main program
public class UseCase11ConcurrentBookingSimulation {
    public static void main(String[] args) throws InterruptedException {
        InventoryManager manager = new InventoryManager();

        // Shared booking queue
        Queue<Reservation> bookingQueue = new ConcurrentLinkedQueue<>();

        // Simulate multiple guests submitting requests
        bookingQueue.add(new Reservation("R001", "Alice", "Single", 2));
        bookingQueue.add(new Reservation("R002", "Bob", "Double", 1));
        bookingQueue.add(new Reservation("R003", "Charlie", "Suite", 1));
        bookingQueue.add(new Reservation("R004", "Diana", "Single", 1));
        bookingQueue.add(new Reservation("R005", "Eve", "Double", 3));

        List<Thread> threads = new ArrayList<>();
        while (!bookingQueue.isEmpty()) {
            Reservation res = bookingQueue.poll();
            Thread t = new Thread(new BookingTask(manager, res));
            t.setName("GuestThread-" + res.getReservationId());
            threads.add(t);
            t.start();
        }

        // Wait for all threads to finish
        for (Thread t : threads) {
            t.join();
        }

        // Display final results
        System.out.println("\n=== Final Booking State ===");
        manager.displayConfirmedBookings();
        manager.displayInventory();
    }
}