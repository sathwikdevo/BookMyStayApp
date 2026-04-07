import java.util.*;

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

// Inventory Manager with rollback support
class InventoryManager {
    private Map<String, Integer> roomInventory;
    private Map<String, Reservation> confirmedReservations; // Track confirmed bookings
    private Stack<String> rollbackStack; // Stack for last cancelled reservations

    public InventoryManager() {
        roomInventory = new HashMap<>();
        roomInventory.put("Single", 5);
        roomInventory.put("Double", 5);
        roomInventory.put("Suite", 2);
        confirmedReservations = new HashMap<>();
        rollbackStack = new Stack<>();
    }

    // Validate and confirm a booking
    public void validateAndBook(Reservation res) throws InvalidBookingException {
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

        // All validations passed, update inventory and record booking
        roomInventory.put(type, available - requested);
        confirmedReservations.put(res.getReservationId(), res);
        System.out.println("Booking confirmed: " + res);
    }

    // Cancel a booking safely
    public void cancelBooking(String reservationId) throws InvalidBookingException {
        if (!confirmedReservations.containsKey(reservationId)) {
            throw new InvalidBookingException("Reservation not found or already cancelled: " + reservationId);
        }

        Reservation res = confirmedReservations.get(reservationId);
        // Restore inventory
        String type = res.getRoomType();
        int current = roomInventory.get(type);
        roomInventory.put(type, current + res.getNumberOfRooms());

        // Record in rollback stack
        rollbackStack.push(reservationId);
        confirmedReservations.remove(reservationId);
        System.out.println("Booking cancelled successfully: " + res);
    }

    public void displayInventory() {
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

    public void displayRollbackHistory() {
        if (rollbackStack.isEmpty()) {
            System.out.println("No cancellations yet.");
            return;
        }
        System.out.println("\nRollback History (Most Recent First):");
        rollbackStack.forEach(id -> System.out.println("Cancelled Reservation ID: " + id));
    }
}

// Main program
public class UseCase10BookingCancellation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InventoryManager inventoryManager = new InventoryManager();

        boolean running = true;
        System.out.println("=== Book My Stay – Booking Cancellation & Inventory Rollback ===");

        while (running) {
            System.out.println("\nOptions:");
            System.out.println("1. Make a Booking");
            System.out.println("2. Cancel a Booking");
            System.out.println("3. View Inventory");
            System.out.println("4. View Confirmed Bookings");
            System.out.println("5. View Rollback History");
            System.out.println("6. Exit");
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
                        inventoryManager.validateAndBook(reservation);
                    } catch (InvalidBookingException e) {
                        System.out.println("Booking failed: " + e.getMessage());
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input type. Please try again.");
                        scanner.nextLine(); // clear scanner buffer
                    }
                    break;

                case 2:
                    try {
                        System.out.print("Enter Reservation ID to cancel: ");
                        String cancelId = scanner.nextLine();
                        inventoryManager.cancelBooking(cancelId);
                    } catch (InvalidBookingException e) {
                        System.out.println("Cancellation failed: " + e.getMessage());
                    }
                    break;

                case 3:
                    inventoryManager.displayInventory();
                    break;

                case 4:
                    inventoryManager.displayConfirmedBookings();
                    break;

                case 5:
                    inventoryManager.displayRollbackHistory();
                    break;

                case 6:
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