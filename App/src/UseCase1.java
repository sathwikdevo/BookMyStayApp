import java.util.*;

// Simple Reservation class
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

// Booking history class
class BookingHistory {
    private List<Reservation> confirmedReservations;

    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }

    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
        System.out.println("Reservation added to history: " + reservation.getReservationId());
    }

    public List<Reservation> getAllReservations() {
        return Collections.unmodifiableList(confirmedReservations);
    }
}

// Reporting service
class BookingReportService {
    private BookingHistory bookingHistory;

    public BookingReportService(BookingHistory bookingHistory) {
        this.bookingHistory = bookingHistory;
    }

    public void displayAllReservations() {
        System.out.println("\n=== All Confirmed Reservations ===");
        for (Reservation r : bookingHistory.getAllReservations()) {
            System.out.println(r);
        }
    }

    public void displaySummaryByRoomType() {
        System.out.println("\n=== Summary by Room Type ===");
        Map<String, Integer> summary = new HashMap<>();
        for (Reservation r : bookingHistory.getAllReservations()) {
            summary.put(r.getRoomType(), summary.getOrDefault(r.getRoomType(), 0) + r.getNumberOfRooms());
        }
        summary.forEach((roomType, count) -> System.out.println(roomType + ": " + count + " rooms booked"));
    }
}

// Main program
public class UseCase8 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService(history);

        boolean running = true;
        System.out.println("=== Book My Stay – Booking History & Reporting ===");

        while (running) {
            System.out.println("\nOptions:");
            System.out.println("1. Add Confirmed Reservation");
            System.out.println("2. View All Reservations");
            System.out.println("3. View Summary by Room Type");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
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
                    history.addReservation(reservation);
                    break;

                case 2:
                    reportService.displayAllReservations();
                    break;

                case 3:
                    reportService.displaySummaryByRoomType();
                    break;

                case 4:
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