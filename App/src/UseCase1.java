import java.util.*;

// Simple Reservation class
class Reservation {
    private String reservationId;
    private String guestName;

    public Reservation(String reservationId, String guestName) {
        this.reservationId = reservationId;
        this.guestName = guestName;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }

    @Override
    public String toString() {
        return "Reservation{" + "ID='" + reservationId + "', Guest='" + guestName + "'}";
    }
}

// Add-On Service class
class Service {
    private String serviceName;
    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public String getServiceName() { return serviceName; }
    public double getCost() { return cost; }

    @Override
    public String toString() {
        return serviceName + " ($" + cost + ")";
    }
}

// Manager for Add-On Services
class AddOnServiceManager {
    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    // Add services for a reservation
    public void addServices(String reservationId, List<Service> services) {
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).addAll(services);
        System.out.println("Added services for reservation " + reservationId);
    }

    // Display services for all reservations
    public void displayServices(Map<String, Reservation> reservations) {
        System.out.println("\nReservation Services:");
        for (String resId : reservationServices.keySet()) {
            Reservation res = reservations.get(resId);
            System.out.println(res + " -> " + reservationServices.get(resId));
        }
    }

    // Calculate total additional cost for a reservation
    public double calculateTotalCost(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>())
                .stream()
                .mapToDouble(Service::getCost)
                .sum();
    }
}

// Main program
public class UseCase7 {
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Map<String, Reservation> reservations = new HashMap<>();
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        // Sample reservations
        reservations.put("R101", new Reservation("R101", "Alice"));
        reservations.put("R102", new Reservation("R102", "Bob"));

        boolean running = true;
        System.out.println("=== Book My Stay – Add-On Service Selection ===");

        while (running) {
            System.out.println("\nOptions:");
            System.out.println("1. View Reservations");
            System.out.println("2. Add Services to Reservation");
            System.out.println("3. View Reservation Services");
            System.out.println("4. Calculate Total Service Cost");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    reservations.values().forEach(System.out::println);
                    break;

                case 2:
                    System.out.print("Enter Reservation ID: ");
                    String resId = scanner.nextLine();
                    if (!reservations.containsKey(resId)) {
                        System.out.println("Invalid Reservation ID.");
                        break;
                    }

                    List<Service> servicesToAdd = new ArrayList<>();
                    boolean adding = true;
                    while (adding) {
                        System.out.print("Enter Service Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Service Cost: ");
                        double cost = scanner.nextDouble();
                        scanner.nextLine();
                        servicesToAdd.add(new Service(name, cost));

                        System.out.print("Add another service? (y/n): ");
                        adding = scanner.nextLine().equalsIgnoreCase("y");
                    }

                    serviceManager.addServices(resId, servicesToAdd);
                    break;

                case 3:
                    serviceManager.displayServices(reservations);
                    break;

                case 4:
                    System.out.print("Enter Reservation ID to calculate total service cost: ");
                    String id = scanner.nextLine();
                    double total = serviceManager.calculateTotalCost(id);
                    System.out.println("Total additional cost for " + id + ": $" + total);
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