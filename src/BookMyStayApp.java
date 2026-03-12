import java.util.*;

// Class representing an Add-On Service
class AddOnService {
    private String serviceName;
    private double serviceCost;

    public AddOnService(String serviceName, double serviceCost) {
        this.serviceName = serviceName;
        this.serviceCost = serviceCost;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getServiceCost() {
        return serviceCost;
    }

    @Override
    public String toString() {
        return serviceName + " (Rs." + serviceCost + ")";
    }
}

// Manager class to handle services for reservations
class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private Map<String, List<AddOnService>> reservationServices = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        reservationServices.get(reservationId).add(service);

        System.out.println(service.getServiceName() + " added to reservation " + reservationId);
    }

    // Display services for reservation
    public void displayServices(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected for reservation " + reservationId);
            return;
        }

        System.out.println("\nServices for Reservation " + reservationId + ":");

        for (AddOnService s : services) {
            System.out.println("- " + s);
        }
    }

    // Calculate total cost of services
    public double calculateTotalCost(String reservationId) {

        List<AddOnService> services = reservationServices.get(reservationId);

        if (services == null) {
            return 0;
        }

        double total = 0;

        for (AddOnService s : services) {
            total += s.getServiceCost();
        }

        return total;
    }
}

// Main class
public class BookMyStayApp{

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AddOnServiceManager manager = new AddOnServiceManager();

        System.out.print("Enter Reservation ID: ");
        String reservationId = sc.nextLine();

        System.out.print("How many services do you want to add? ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= n; i++) {

            System.out.print("Enter Service Name: ");
            String name = sc.nextLine();

            System.out.print("Enter Service Cost: ");
            double cost = sc.nextDouble();
            sc.nextLine();

            AddOnService service = new AddOnService(name, cost);
            manager.addService(reservationId, service);
        }

        manager.displayServices(reservationId);

        double total = manager.calculateTotalCost(reservationId);

        System.out.println("\nTotal Additional Service Cost: Rs." + total);

        sc.close();
    }
}