import java.util.*;

// Reservation class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private int nights;

    public Reservation(String reservationId, String guestName, String roomType, int nights) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.nights = nights;
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

    public int getNights() {
        return nights;
    }

    @Override
    public String toString() {
        return "Reservation ID: " + reservationId +
                ", Guest: " + guestName +
                ", Room: " + roomType +
                ", Nights: " + nights;
    }
}

// Booking History class
class BookingHistory {

    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation reservation) {
        history.add(reservation);
        System.out.println("Booking confirmed and added to history.");
    }

    // Get all reservations
    public List<Reservation> getReservations() {
        return history;
    }
}

// Reporting Service
class BookingReportService {

    // Display booking history
    public void showBookingHistory(List<Reservation> reservations) {

        if (reservations.isEmpty()) {
            System.out.println("No booking history available.");
            return;
        }

        System.out.println("\n--- Booking History ---");

        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }

    // Generate summary report
    public void generateSummaryReport(List<Reservation> reservations) {

        System.out.println("\n--- Booking Summary Report ---");

        int totalBookings = reservations.size();
        int totalNights = 0;

        for (Reservation r : reservations) {
            totalNights += r.getNights();
        }

        System.out.println("Total Reservations: " + totalBookings);
        System.out.println("Total Nights Booked: " + totalNights);
    }
}

// Main class
public class BookMyStayApp{

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BookingHistory history = new BookingHistory();
        BookingReportService reportService = new BookingReportService();

        System.out.print("Enter number of bookings to confirm: ");
        int n = sc.nextInt();
        sc.nextLine();

        for (int i = 1; i <= n; i++) {

            System.out.println("\nEnter details for booking " + i);

            System.out.print("Reservation ID: ");
            String id = sc.nextLine();

            System.out.print("Guest Name: ");
            String guest = sc.nextLine();

            System.out.print("Room Type: ");
            String room = sc.nextLine();

            System.out.print("Number of Nights: ");
            int nights = sc.nextInt();
            sc.nextLine();

            Reservation reservation = new Reservation(id, guest, room, nights);

            history.addReservation(reservation);
        }

        // Admin views booking history
        reportService.showBookingHistory(history.getReservations());

        // Admin generates report
        reportService.generateSummaryReport(history.getReservations());

        sc.close();
    }
}