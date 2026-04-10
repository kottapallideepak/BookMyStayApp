import java.util.HashMap;
import java.util.Map;

/**
 * =============================================================
 * MAIN CLASS - BookMyStayApp
 * =============================================================
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * @version 3.0
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

// -------------------- INVENTORY CLASS --------------------
class RoomInventory {

    private Map<String, Integer> availability;

    public RoomInventory() {
        availability = new HashMap<>();
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public int getAvailability(String roomType) {
        return availability.get(roomType);
    }
}

// -------------------- MAIN CLASS --------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Hotel Room Inventory\n");

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        RoomInventory inventory = new RoomInventory();

        System.out.println("Single Room:");
        single.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Single") + "\n");

        System.out.println("Double Room:");
        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Double") + "\n");

        System.out.println("Suite Room:");
        suite.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Suite"));
    }
}