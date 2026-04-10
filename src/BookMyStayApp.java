import java.util.*;

/**
 * =============================================================
 * MAIN CLASS - BookMyStayApp
 * =============================================================
 *
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 *
 * @version 11.0
 */

// -------------------- INVENTORY --------------------
class RoomInventory {

    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 2);
    }

    // synchronized critical section
    public synchronized boolean bookRoom(String type, String guest) {

        int available = availability.getOrDefault(type, 0);

        if (available > 0) {
            System.out.println(guest + " is booking...");

            availability.put(type, available - 1);

            System.out.println("Booking successful for " + guest +
                    " | Remaining: " + availability.get(type));

            return true;
        } else {
            System.out.println("Booking failed for " + guest + " (No rooms left)");
            return false;
        }
    }
}

// -------------------- BOOKING TASK (THREAD) --------------------
class BookingTask implements Runnable {

    private RoomInventory inventory;
    private String guestName;

    public BookingTask(RoomInventory inventory, String guestName) {
        this.inventory = inventory;
        this.guestName = guestName;
    }

    @Override
    public void run() {
        inventory.bookRoom("Single", guestName);
    }
}

// -------------------- MAIN --------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation\n");

        RoomInventory inventory = new RoomInventory();

        // Multiple guests (threads)
        Thread t1 = new Thread(new BookingTask(inventory, "Alice"));
        Thread t2 = new Thread(new BookingTask(inventory, "Bob"));
        Thread t3 = new Thread(new BookingTask(inventory, "Charlie"));

        // Start threads
        t1.start();
        t2.start();
        t3.start();
    }
}