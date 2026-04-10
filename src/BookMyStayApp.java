import java.io.*;
import java.util.*;

/**
 * =============================================================
 * MAIN CLASS - BookMyStayApp
 * =============================================================
 *
 * Use Case 12: Data Persistence & System Recovery
 *
 * @version 12.0
 */

// -------------------- INVENTORY --------------------
class RoomInventory implements Serializable {

    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 2);
        availability.put("Double", 1);
    }

    public Map<String, Integer> getAvailability() {
        return availability;
    }

    public void display() {
        System.out.println("Inventory:");
        for (String key : availability.keySet()) {
            System.out.println(key + " → " + availability.get(key));
        }
    }
}

// -------------------- BOOKING HISTORY --------------------
class BookingHistory implements Serializable {

    private List<String> bookings = new ArrayList<>();

    public void add(String booking) {
        bookings.add(booking);
    }

    public List<String> getAll() {
        return bookings;
    }

    public void display() {
        System.out.println("\nBooking History:");
        for (String b : bookings) {
            System.out.println(b);
        }
    }
}

// -------------------- PERSISTENCE SERVICE --------------------
class PersistenceService {

    private static final String FILE_NAME = "data.ser";

    // Save data
    public void save(RoomInventory inventory, BookingHistory history) {

        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(inventory);
            oos.writeObject(history);

            System.out.println("\nData saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving data.");
        }
    }

    // Load data
    public Object[] load() {

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            RoomInventory inventory = (RoomInventory) ois.readObject();
            BookingHistory history = (BookingHistory) ois.readObject();

            System.out.println("Data loaded successfully.\n");

            return new Object[]{inventory, history};

        } catch (Exception e) {
            System.out.println("No previous data found. Starting fresh.\n");
            return null;
        }
    }
}

// -------------------- MAIN --------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Data Persistence & Recovery System\n");

        PersistenceService service = new PersistenceService();

        RoomInventory inventory;
        BookingHistory history;

        // Load previous state
        Object[] data = service.load();

        if (data != null) {
            inventory = (RoomInventory) data[0];
            history = (BookingHistory) data[1];
        } else {
            inventory = new RoomInventory();
            history = new BookingHistory();
        }

        // Simulate new activity
        history.add("R101 - Alice - Single");
        history.add("R102 - Bob - Double");

        // Display current state
        inventory.display();
        history.display();

        // Save state
        service.save(inventory, history);
    }
}