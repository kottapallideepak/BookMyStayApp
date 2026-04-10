import java.util.*;

/**
 * =============================================================
 * MAIN CLASS - BookMyStayApp
 * =============================================================
 *
 * Use Case 9: Error Handling & Validation
 *
 * @version 9.0
 */

// -------------------- CUSTOM EXCEPTION --------------------
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

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

// -------------------- INVENTORY --------------------
class RoomInventory {

    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 1);
        availability.put("Double", 1);
        availability.put("Suite", 0);
    }

    public int getAvailable(String type) {
        return availability.getOrDefault(type, -1);
    }

    public void decrease(String type) {
        availability.put(type, availability.get(type) - 1);
    }
}

// -------------------- VALIDATOR --------------------
class BookingValidator {

    private static final List<String> validTypes =
            Arrays.asList("Single", "Double", "Suite");

    public void validate(Reservation r, RoomInventory inventory)
            throws InvalidBookingException {

        // Check valid room type
        if (!validTypes.contains(r.getRoomType())) {
            throw new InvalidBookingException("Invalid room type: " + r.getRoomType());
        }

        // Check availability
        int available = inventory.getAvailable(r.getRoomType());

        if (available <= 0) {
            throw new InvalidBookingException(
                    "No rooms available for: " + r.getRoomType());
        }
    }
}

// -------------------- BOOKING SERVICE --------------------
class BookingService {

    private BookingValidator validator = new BookingValidator();

    public void processBooking(Reservation r, RoomInventory inventory) {

        try {
            // Validate first (fail-fast)
            validator.validate(r, inventory);

            // Safe allocation
            inventory.decrease(r.getRoomType());

            System.out.println("Booking Successful:");
            System.out.println("Guest: " + r.getGuestName());
            System.out.println("Room: " + r.getRoomType() + "\n");

        } catch (InvalidBookingException e) {

            System.out.println("Booking Failed:");
            System.out.println(e.getMessage() + "\n");
        }
    }
}

// -------------------- MAIN --------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Error Handling & Validation System\n");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService();

        // Test cases
        service.processBooking(new Reservation("Alice", "Single"), inventory);
        service.processBooking(new Reservation("Bob", "Suite"), inventory);   // no availability
        service.processBooking(new Reservation("Charlie", "Deluxe"), inventory); // invalid type
    }
}