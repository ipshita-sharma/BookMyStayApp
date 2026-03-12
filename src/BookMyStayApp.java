import java.io.*;
import java.util.*;

// Reservation class (Serializable)
class Reservation implements Serializable {
    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return reservationId + " | " + guestName + " | " + roomType;
    }
}

// System State class (Serializable)
class SystemState implements Serializable {
    List<Reservation> bookingHistory;
    Map<String, Integer> inventory;

    public SystemState(List<Reservation> bookingHistory, Map<String, Integer> inventory) {
        this.bookingHistory = bookingHistory;
        this.inventory = inventory;
    }
}

// Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "hotel_state.dat";

    // Save system state
    public static void saveState(SystemState state) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(state);
            System.out.println("System state saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving system state.");
        }
    }

    // Load system state
    public static SystemState loadState() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("System state restored from file.");
            return (SystemState) in.readObject();
        } catch (Exception e) {
            System.out.println("No previous data found. Starting with fresh state.");
            return null;
        }
    }
}

// Main class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        List<Reservation> bookingHistory = new ArrayList<>();
        Map<String, Integer> inventory = new HashMap<>();

        // Default inventory
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);

        // Attempt recovery
        SystemState recovered = PersistenceService.loadState();

        if (recovered != null) {
            bookingHistory = recovered.bookingHistory;
            inventory = recovered.inventory;
        }

        System.out.println("\nCurrent Inventory:");
        for (String room : inventory.keySet()) {
            System.out.println(room + " : " + inventory.get(room));
        }

        System.out.print("\nEnter Reservation ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Guest Name: ");
        String guest = sc.nextLine();

        System.out.print("Enter Room Type (Standard/Deluxe/Suite): ");
        String roomType = sc.nextLine();

        if (!inventory.containsKey(roomType) || inventory.get(roomType) <= 0) {
            System.out.println("Booking failed: Room not available.");
        } else {
            inventory.put(roomType, inventory.get(roomType) - 1);

            Reservation r = new Reservation(id, guest, roomType);
            bookingHistory.add(r);

            System.out.println("Booking confirmed: " + r);
        }

        System.out.println("\nBooking History:");
        for (Reservation r : bookingHistory) {
            System.out.println(r);
        }

        // Save state before shutdown
        SystemState state = new SystemState(bookingHistory, inventory);
        PersistenceService.saveState(state);

        sc.close();
    }
}