import java.util.*;

// Reservation class
class Reservation {
    String reservationId;
    String roomType;
    String roomId;
    boolean active;

    public Reservation(String reservationId, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }

    public void cancel() {
        active = false;
    }

    public boolean isActive() {
        return active;
    }

    public String toString() {
        return "ReservationID: " + reservationId +
                ", RoomType: " + roomType +
                ", RoomID: " + roomId +
                ", Status: " + (active ? "Confirmed" : "Cancelled");
    }
}

// Inventory Manager
class InventoryManager {

    Map<String, Integer> inventory = new HashMap<>();

    public InventoryManager() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.get(roomType) + 1);
    }

    public boolean available(String roomType) {
        return inventory.containsKey(roomType) && inventory.get(roomType) > 0;
    }

    public void displayInventory() {
        System.out.println("\nCurrent Inventory:");
        for (String type : inventory.keySet()) {
            System.out.println(type + " : " + inventory.get(type));
        }
    }
}

// Cancellation Service
class CancellationService {

    Stack<String> rollbackStack = new Stack<>();

    public void cancelReservation(String reservationId,
                                  Map<String, Reservation> reservations,
                                  InventoryManager inventory) {

        if (!reservations.containsKey(reservationId)) {
            System.out.println("Cancellation Failed: Reservation does not exist.");
            return;
        }

        Reservation r = reservations.get(reservationId);

        if (!r.isActive()) {
            System.out.println("Cancellation Failed: Reservation already cancelled.");
            return;
        }

        // Record room ID for rollback
        rollbackStack.push(r.roomId);

        // Restore inventory
        inventory.increment(r.roomType);

        // Mark reservation cancelled
        r.cancel();

        System.out.println("Reservation cancelled successfully.");
        System.out.println("Released Room ID: " + rollbackStack.peek());
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        InventoryManager inventory = new InventoryManager();
        Map<String, Reservation> reservations = new HashMap<>();
        CancellationService cancelService = new CancellationService();

        System.out.println("=== Book My Stay App ===");

        inventory.displayInventory();

        // Booking
        System.out.print("\nEnter Reservation ID: ");
        String resId = sc.nextLine();

        System.out.print("Enter Room Type (Standard/Deluxe/Suite): ");
        String roomType = sc.nextLine();

        if (!inventory.available(roomType)) {
            System.out.println("Booking Failed: No rooms available.");
            return;
        }

        String roomId = roomType.substring(0,1) + (new Random().nextInt(100)+1);

        Reservation reservation = new Reservation(resId, roomType, roomId);

        reservations.put(resId, reservation);

        inventory.decrement(roomType);

        System.out.println("Booking Confirmed!");
        System.out.println(reservation);

        inventory.displayInventory();

        // Cancellation
        System.out.print("\nEnter Reservation ID to cancel: ");
        String cancelId = sc.nextLine();

        cancelService.cancelReservation(cancelId, reservations, inventory);

        System.out.println("\nUpdated Reservation Record:");
        System.out.println(reservations.get(cancelId));

        inventory.displayInventory();

        sc.close();
    }
}