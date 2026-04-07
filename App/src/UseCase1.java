import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

// Class representing a guest's reservation request
class Reservation {
    private String guestName;
    private int numberOfRooms;

    public Reservation(String guestName, int numberOfRooms) {
        this.guestName = guestName;
        this.numberOfRooms = numberOfRooms;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    @Override
    public String toString() {
        return "Reservation{Guest='" + guestName + "', Rooms=" + numberOfRooms + "}";
    }
}

// Main class for Use Case 5
public class UseCase5{
    private Queue<Reservation> bookingQueue;

    public UseCase5BookingRequestQueue() {
        bookingQueue = new LinkedList<>();
    }

    // Method to add a booking request
    public void addBookingRequest(Reservation reservation) {
        bookingQueue.add(reservation);
        System.out.println("Booking request added for " + reservation.getGuestName());
    }

    // Method to display queued requests
    public void displayQueue() {
        System.out.println("\nCurrent Booking Queue (FIFO order):");
        if (bookingQueue.isEmpty()) {
            System.out.println("No pending booking requests.");
        } else {
            for (Reservation r : bookingQueue) {
                System.out.println(r);
            }
        }
    }

    // Main program flow
    public static void main(String[] args) {
        UseCase5BookingRequestQueue system = new UseCase5BookingRequestQueue();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=== Welcome to Book My Stay (Booking Request Queue) ===");

        while (running) {
            System.out.println("\nOptions:");
            System.out.println("1. Submit Booking Request");
            System.out.println("2. View Booking Queue");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter guest name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter number of rooms: ");
                    int rooms = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    Reservation reservation = new Reservation(name, rooms);
                    system.addBookingRequest(reservation);
                    break;

                case 2:
                    system.displayQueue();
                    break;

                case 3:
                    running = false;
                    System.out.println("Exiting system...");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }
}