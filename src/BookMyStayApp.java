import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

// Class representing a guest's reservation intent
class Reservation {
    private String guestName;
    private int roomNumber; // Requested room number

    public Reservation(String guestName, int roomNumber) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    @Override
    public String toString() {
        return "Reservation[Guest: " + guestName + ", Room: " + roomNumber + "]";
    }
}

// Main class for Use Case 5
public class BookMyStayApp {

    // Queue to store incoming booking requests
    private Queue<Reservation> bookingQueue;

    public BookMyStayApp() {
        bookingQueue = new LinkedList<>();
    }

    // Method to submit a booking request
    public void submitBookingRequest(String guestName, int roomNumber) {
        Reservation reservation = new Reservation(guestName, roomNumber);
        bookingQueue.add(reservation);
        System.out.println("Booking request added to queue: " + reservation);
    }

    // Method to process booking requests (for demonstration)
    public void processBookingRequests() {
        System.out.println("\nProcessing booking requests in arrival order:");
        while (!bookingQueue.isEmpty()) {
            Reservation reservation = bookingQueue.poll();
            // Allocation logic would go here; currently just display
            System.out.println("Processing: " + reservation);
        }
        System.out.println("All booking requests have been processed.");
    }

    public static void main(String[] args) {
        BookMyStayApp bookingSystem = new BookMyStayApp();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("=== Welcome to Book My Stay: Booking Request Queue ===");

        while (!exit) {
            System.out.println("\n1. Submit Booking Request");
            System.out.println("2. Process Booking Requests");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Guest Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Requested Room Number: ");
                    int room = scanner.nextInt();
                    bookingSystem.submitBookingRequest(name, room);
                    break;
                case 2:
                    bookingSystem.processBookingRequests();
                    break;
                case 3:
                    exit = true;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }
}