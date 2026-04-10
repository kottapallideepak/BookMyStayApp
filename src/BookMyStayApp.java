import java.util.*;

/**
 * =============================================================
 * MAIN CLASS - BookMyStayApp
 * =============================================================
 *
 * Use Case 7: Add-On Service Selection
 *
 * @version 7.0
 */

// -------------------- RESERVATION --------------------
class Reservation {

    private String reservationId;
    private String guestName;

    public Reservation(String reservationId, String guestName) {
        this.reservationId = reservationId;
        this.guestName = guestName;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }
}

// -------------------- SERVICE --------------------
class AddOnService {

    private String serviceName;
    private double cost;

    public AddOnService(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }

    public double getCost() {
        return cost;
    }

    public void display() {
        System.out.println(serviceName + " - ₹" + cost);
    }
}

// -------------------- SERVICE MANAGER --------------------
class AddOnServiceManager {

    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to reservation
    public void addService(String reservationId, AddOnService service) {

        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);
    }

    // Display services
    public void displayServices(String reservationId) {

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No add-on services selected.");
            return;
        }

        double total = 0;

        System.out.println("\nAdd-On Services:");

        for (AddOnService s : services) {
            s.display();
            total += s.getCost();
        }

        System.out.println("Total Add-On Cost: ₹" + total);
    }
}

// -------------------- MAIN --------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Add-On Service System\n");

        // Sample reservation
        Reservation reservation = new Reservation("R101", "Alice");

        // Service manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // Adding services
        manager.addService("R101", new AddOnService("Breakfast", 300));
        manager.addService("R101", new AddOnService("Airport Pickup", 800));
        manager.addService("R101", new AddOnService("Spa Access", 1200));

        // Display
        System.out.println("Reservation ID: " + reservation.getReservationId());
        System.out.println("Guest: " + reservation.getGuestName());

        manager.displayServices("R101");
    }
}