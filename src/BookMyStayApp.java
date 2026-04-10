import java.util.*;

/**
 * =============================================================
 * MAIN CLASS - BookMyStayApp
 * =============================================================
 *
 * Use Case 8: Booking History & Reporting
 *
 * @version 8.0
 */

// -------------------- RESERVATION --------------------
class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// -------------------- BOOKING HISTORY --------------------
class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    // Store confirmed reservation
    public void addReservation(Reservation r) {
        history.add(r);
    }

    // Retrieve all bookings
    public List<Reservation> getAllBookings() {
        return history;
    }
}

// -------------------- REPORT SERVICE --------------------
class BookingReportService {

    public void generateReport(List<Reservation> bookings) {

        System.out.println("\nBooking Report:\n");

        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        Map<String, Integer> summary = new HashMap<>();

        for (Reservation r : bookings) {
            r.display();

            // Count per room type
            summary.put(r.getRoomType(),
                    summary.getOrDefault(r.getRoomType(), 0) + 1);
        }

        System.out.println("\nSummary:");

        for (String type : summary.keySet()) {
            System.out.println(type + " Rooms Booked: " + summary.get(type));
        }
    }
}

// -------------------- MAIN --------------------
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking History & Reporting System\n");

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        // Simulating confirmed bookings
        history.addReservation(new Reservation("R101", "Alice", "Single"));
        history.addReservation(new Reservation("R102", "Bob", "Double"));
        history.addReservation(new Reservation("R103", "Charlie", "Single"));
        history.addReservation(new Reservation("R104", "David", "Suite"));

        // Generate report
        reportService.generateReport(history.getAllBookings());
    }
}