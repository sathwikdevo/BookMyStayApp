import java.util.*;

// Reservation class (from previous use case)
class Reservation {
    private String guestName;
    private int numberOfRooms;
    private String roomType;

    public Reservation(String guestName, int numberOfRooms, String roomType) {
        this.guestName = guestName;
        this.numberOfRooms = numberOfRooms;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public int getNumberOfRooms() { return numberOfRooms; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return "Reservation{Guest='" + guestName + "', Rooms=" + numberOfRooms + ", Type=" + roomType + "}";
    }
}

// Inventory Service to track room availability
class InventoryService {
    private Map<String, Integer> roomInventory;

    public InventoryService() {
        roomInventory = new HashMap<>();
        roomInventory.put("Single", 5);
        roomInventory.put("Double", 5);
        roomInventory.put("Suite", 2);
    }

    public boolean isAvailable(String roomType, int quantity) {
        return roomInventory.getOrDefault(roomType, 0) >= quantity;
    }

    public void allocateRooms(String roomType, int quantity) {
        roomInventory.put(roomType, roomInventory.get(roomType) - quantity);
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (Map.Entry<String, Integer> entry : roomInventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " rooms available");
        }
    }
}

// Main Booking Allocation Service
public class UseCase6{
    private Queue<Reservation> bookingQueue;
    private InventoryService inventoryService;
    private Map<String, Set<String>> allocatedRooms;

    public UseCase6RoomAllocationService() {
        bookingQueue = new LinkedList<>();
        inventoryService = new InventoryService();
        allocatedRooms = new HashMap<>();
    }

    // Add request to queue
    public void addBookingRequest(Reservation r) {
        bookingQueue.add(r);
        System.out.println("Booking request added for " + r.getGuestName());
    }

    // Confirm and allocate rooms
    public void processBookingQueue() {
        while (!bookingQueue.isEmpty()) {
            Reservation r = bookingQueue.poll();
            String type = r.getRoomType();
            int qty = r.getNumberOfRooms();

            if (inventoryService.isAvailable(type, qty)) {
                inventoryService.allocateRooms(type, qty);
                allocatedRooms.putIfAbsent(type, new HashSet<>());

                // Generate unique room IDs
                for (int i = 1; i <= qty; i++) {
                    String roomId;
                    do {
                        roomId = type.substring(0, 1).toUpperCase() + (new Random().nextInt(100) + 1);
                    } while (allocatedRooms.get(type).contains(roomId));
                    allocatedRooms.get(type).add(roomId);
                    System.out.println("Allocated Room " + roomId + " to " + r.getGuestName());
                }

            } else {
                System.out.println("Insufficient " + type + " rooms for " + r.getGuestName() + ". Request pending.");
            }
        }
    }

    public void displayAllocatedRooms() {
        System.out.println("\nAllocated Rooms:");
        for (String type : allocatedRooms.keySet()) {
            System.out.println(type + ": " + allocatedRooms.get(type));
        }
    }

    // Main Program
    public static void main(String[] args) {
        UseCase6RoomAllocationService system = new UseCase6RoomAllocationService();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Book My Stay – Room Allocation Service ===");

        while (running) {
            System.out.println("\nOptions:");
            System.out.println("1. Submit Booking Request");
            System.out.println("2. Process Booking Queue");
            System.out.println("3. View Inventory");
            System.out.println("4. View Allocated Rooms");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter guest name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter room type (Single/Double/Suite): ");
                    String type = scanner.nextLine();
                    System.out.print("Enter number of rooms: ");
                    int qty = scanner.nextInt();
                    scanner.nextLine();
                    system.addBookingRequest(new Reservation(name, qty, type));
                    break;

                case 2:
                    system.processBookingQueue();
                    break;

                case 3:
                    system.inventoryService.displayInventory();
                    break;

                case 4:
                    system.displayAllocatedRooms();
                    break;

                case 5:
                    running = false;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }

        scanner.close();
    }
}