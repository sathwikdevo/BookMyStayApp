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

// Inventory Manager
class InventoryManager {
    private Map<String, Integer> roomInventory;

    public InventoryManager() {
        roomInventory = new HashMap<>();
        roomInventory.put("Single", 5);
        roomInventory.put("Double", 5);
        roomInventory.put("Suite", 2);
    }

    public void validateAndBook(Reservation res) throws InvalidBookingException {
        String type = res.getRoomType();
        int requested = res.getNumberOfRooms();

        if (!roomInventory.containsKey(type)) {
            throw new InvalidBookingException("Invalid room type: " + type);
        }

        int available = roomInventory.get(type);
        if (requested <= 0) {
            throw new InvalidBookingException("Number of rooms must be positive.");
        }

        if (requested > available) {
            throw new InvalidBookingException("Not enough rooms available. Requested: " + requested + ", Available: " + available);
        }

        // All validations passed, update inventory
        roomInventory.put(type, available - requested);
        System.out.println("Booking confirmed: " + res);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        roomInventory.forEach((type, count) -> System.out.println(type + ": " + count + " rooms available"));
    }
}

// Main program
public class UseCase9ErrorHandlingValidation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        InventoryManager inventoryManager = new InventoryManager();

        boolean running = true;
        System.out.println("=== Book My Stay – Error Handling & Validation ===");

        while (running) {
            System.out.println("\nOptions:");
            System.out.println("1. Make a Booking");
            System.out.println("2. View Inventory");
            System.out.println("3. Exit");
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
                    inventoryManager.displayInventory();
                    break;

                case 3:
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