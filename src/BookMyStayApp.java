import java.util.LinkedList;
import java.util.Queue;

/**
 * =============================================================
 * MAIN CLASS - BookMyStayApp
 * =============================================================
 *
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * @version 5.0
 */

// -------------------- RESERVATION CLASS --------------------
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

    public void display() {
        System.out.println("Guest: " + guestName + " | Room Type: " + roomType);
    }
}

// -------------------- BOOKING QUEUE --------------------
class BookingRequestQueue {

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add booking request
    public void addRequest(Reservation reservation) {
        queue.offer(reservation);
        System.out.println("Request added:");
        reservation.display();
    }

    // View all requests (without removing)
    public void displayQueue() {
        System.out.println("\nBooking Requests in Queue:\n");

        for (Reservation r : queue) {
            r.display();
        }
    }
}

// -------------------- MAIN CLASS --------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking Request System (FIFO)\n");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulating incoming requests
        bookingQueue.addRequest(new Reservation("Alice", "Single"));
        bookingQueue.addRequest(new Reservation("Bob", "Double"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite"));

        // Display queue (FIFO order)
        bookingQueue.displayQueue();
    }
}