import java.util.*;

/**
 * =============================================================
 * MAIN CLASS - BookMyStayApp
 * =============================================================
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * @version 6.0
 */

// -------------------- RESERVATION --------------------
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// -------------------- BOOKING QUEUE --------------------
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // FIFO
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// -------------------- INVENTORY --------------------
class RoomInventory {

    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 2);
        availability.put("Double", 1);
        availability.put("Suite", 1);
    }

    public int getAvailable(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void decrease(String type) {
        availability.put(type, availability.get(type) - 1);
    }
}

// -------------------- BOOKING SERVICE --------------------
class BookingService {

    private Map<String, Set<String>> allocatedRooms = new HashMap<>();

    public void processBookings(BookingRequestQueue queue, RoomInventory inventory) {

        while (!queue.isEmpty()) {

            Reservation r = queue.getNextRequest();
            String type = r.getRoomType();

            // Check availability
            if (inventory.getAvailable(type) > 0) {

                String roomId = generateRoomId(type);

                // Ensure set exists
                allocatedRooms.putIfAbsent(type, new HashSet<>());

                // Add unique room ID
                allocatedRooms.get(type).add(roomId);

                // Update inventory immediately
                inventory.decrease(type);

                System.out.println("Booking Confirmed:");
                System.out.println("Guest: " + r.getGuestName());
                System.out.println("Room Type: " + type);
                System.out.println("Room ID: " + roomId + "\n");

            } else {
                System.out.println("Booking Failed (No Availability): " + r.getGuestName() + " - " + type);
            }
        }
    }

    // Generate unique ID
    private String generateRoomId(String type) {
        return type.substring(0, 1).toUpperCase() + UUID.randomUUID().toString().substring(0, 4);
    }
}

// -------------------- MAIN --------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Room Allocation System\n");

        BookingRequestQueue queue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService();

        // Add requests (FIFO)
        queue.addRequest(new Reservation("Alice", "Single"));
        queue.addRequest(new Reservation("Bob", "Single"));
        queue.addRequest(new Reservation("Charlie", "Single")); // exceeds
        queue.addRequest(new Reservation("David", "Double"));

        // Process bookings
        service.processBookings(queue, inventory);
    }
}