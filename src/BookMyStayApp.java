import java.util.HashMap;
import java.util.Map;

/**
 * =============================================================
 * MAIN CLASS - BookMyStayApp
 * =============================================================
 *
 * Use Case 4: Room Search & Availability Check
 *
 * @version 4.0
 */

// -------------------- ABSTRACT CLASS --------------------
abstract class Room {

    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

// -------------------- ROOM TYPES --------------------
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}

// -------------------- INVENTORY --------------------
class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 0); // Example: unavailable
    }

    public Map<String, Integer> getAvailability() {
        return availability;
    }
}

// -------------------- SEARCH SERVICE --------------------
class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory) {

        Map<String, Integer> data = inventory.getAvailability();

        System.out.println("Available Rooms:\n");

        for (String type : data.keySet()) {

            int count = data.get(type);

            // Filter only available rooms
            if (count > 0) {

                Room room = getRoomObject(type);

                System.out.println(type + " Room:");
                room.displayRoomDetails();
                System.out.println("Available: " + count + "\n");
            }
        }
    }

    // Helper method (polymorphism usage)
    private Room getRoomObject(String type) {

        switch (type) {
            case "Single":
                return new SingleRoom();
            case "Double":
                return new DoubleRoom();
            case "Suite":
                return new SuiteRoom();
            default:
                return null;
        }
    }
}

// -------------------- MAIN CLASS --------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Room Search System\n");

        RoomInventory inventory = new RoomInventory();
        RoomSearchService searchService = new RoomSearchService();

        // UC4: Read-only search
        searchService.searchAvailableRooms(inventory);
    }
}