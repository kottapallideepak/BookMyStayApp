import java.util.*;

/**
 * =============================================================
 * MAIN CLASS - BookMyStayApp
 * =============================================================
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * @version 10.0
 */

// -------------------- RESERVATION --------------------
class Reservation {

    private String reservationId;
    private String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }
}

// -------------------- INVENTORY --------------------
class RoomInventory {

    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 1);
        availability.put("Double", 1);
    }

    public void decrease(String type) {
        availability.put(type, availability.get(type) - 1);
    }

    public void increase(String type) {
        availability.put(type, availability.get(type) + 1);
    }

    public int get(String type) {
        return availability.get(type);
    }
}

// -------------------- BOOKING STORAGE --------------------
class BookingStore {

    private Map<String, Reservation> confirmed = new HashMap<>();

    public void add(Reservation r) {
        confirmed.put(r.getReservationId(), r);
    }

    public Reservation get(String id) {
        return confirmed.get(id);
    }

    public void remove(String id) {
        confirmed.remove(id);
    }

    public boolean exists(String id) {
        return confirmed.containsKey(id);
    }
}

// -------------------- CANCELLATION SERVICE --------------------
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancel(String reservationId,
                       BookingStore store,
                       RoomInventory inventory) {

        // Validate existence
        if (!store.exists(reservationId)) {
            System.out.println("Cancellation Failed: Invalid reservation ID\n");
            return;
        }

        Reservation r = store.get(reservationId);

        // Push to rollback stack
        rollbackStack.push(reservationId);

        // Restore inventory
        inventory.increase(r.getRoomType());

        // Remove booking
        store.remove(reservationId);

        System.out.println("Cancellation Successful:");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Room Type Restored: " + r.getRoomType() + "\n");
    }
}

// -------------------- MAIN --------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Cancellation & Rollback System\n");

        RoomInventory inventory = new RoomInventory();
        BookingStore store = new BookingStore();
        CancellationService cancelService = new CancellationService();

        // Simulate confirmed booking
        Reservation r1 = new Reservation("R101", "Single");
        store.add(r1);
        inventory.decrease("Single");

        System.out.println("Before Cancellation - Single Available: " + inventory.get("Single"));

        // Cancel booking
        cancelService.cancel("R101", store, inventory);

        System.out.println("After Cancellation - Single Available: " + inventory.get("Single"));
    }
}