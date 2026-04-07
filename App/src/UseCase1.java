import java.io.*;
import java.util.*;

// Reservation class implements Serializable for persistence
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
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

// InventoryManager class implements Serializable
class InventoryManager implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, Integer> roomInventory = new HashMap<>();
    private Map<String, Reservation> confirmedReservations = new HashMap<>();

    public InventoryManager() {
        // Default inventory if no saved data exists
        roomInventory.put("Single", 5);
        roomInventory.put("Double", 5);
        roomInventory.put("Suite", 2);
    }

    public synchronized void bookRoom(Reservation res) throws Exception {
        String type = res.getRoomType();
        int requested = res.getNumberOfRooms();

        if (!roomInventory.containsKey(type)) {
            throw new Exception("Invalid room type: " + type);
        }
        if (requested <= 0) {
            throw new Exception("Number of rooms must be positive.");
        }

        int available = roomInventory.get(type);
        if (requested > available) {
            throw new Exception("Not enough rooms available. Requested: " + requested + ", Available: " + available);
        }

        roomInventory.put(type, available - requested);
        confirmedReservations.put(res.getReservationId(), res);
        System.out.println("Booking confirmed: " + res);
    }

    public synchronized void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        roomInventory.forEach((type, count) -> System.out.println(type + ": " + count + " rooms available"));
    }

    public synchronized void displayConfirmedBookings() {
        if (confirmedReservations.isEmpty()) {
            System.out.println("No confirmed bookings.");
            return;
        }
        System.out.println("\nConfirmed Bookings:");
        confirmedReservations.values().forEach(System.out::println);
    }

    // Save state to file
    public void saveState(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
            System.out.println("System state saved successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Failed to save state: " + e.getMessage());
        }
    }

    // Load state from file
    public static InventoryManager loadState(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("No saved state found. Starting with default inventory.");
            return new InventoryManager();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            InventoryManager manager = (InventoryManager) ois.readObject();
            System.out.println("System state restored from " + filename);
            return manager;
        } catch (Exception e) {
            System.out.println("Failed to restore state: " + e.getMessage());
            System.out.println("Starting with default inventory.");
            return new InventoryManager();
        }
    }
}

// Main program
public class UseCase12DataPersistenceRecovery {
    private static final String SAVE_FILE = "booking_state.dat";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load persisted state
        InventoryManager manager = InventoryManager.loadState(SAVE_FILE);

        boolean running = true;
        System.out.println("=== Book My Stay – Data Persistence & System Recovery ===");

        while (running) {
            System.out.println("\nOptions:");
            System.out.println("1. Make a Booking");
            System.out.println("2. View Inventory");
            System.out.println("3. View Confirmed Bookings");
            System.out.println("4. Save & Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    try {
                        System.out.print("Enter Reservation ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Enter Guest Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Room Type (Single/Double/Suite): ");
                        String roomType = scanner.nextLine();
                        System.out.print("Enter Number of Rooms: ");
                        int numRooms = scanner.nextInt();
                        scanner.nextLine();

                        Reservation reservation = new Reservation(id, name, roomType, numRooms);
                        manager.bookRoom(reservation);
                    } catch (Exception e) {
                        System.out.println("Booking failed: " + e.getMessage());
                    }
                    break;

                case 2:
                    manager.displayInventory();
                    break;

                case 3:
                    manager.displayConfirmedBookings();
                    break;

                case 4:
                    manager.saveState(SAVE_FILE);
                    running = false;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        scanner.close();
    }
}