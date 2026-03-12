import java.util.*;

// Custom Exception for Invalid Booking
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// Room Inventory Manager
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        // Initial room availability
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 2);
        inventory.put("Suite", 1);
    }

    public void bookRoom(String roomType) throws InvalidBookingException {

        // Validate room type
        if (!inventory.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        int available = inventory.get(roomType);

        // Prevent negative inventory
        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for type: " + roomType);
        }

        // Update inventory
        inventory.put(roomType, available - 1);

        System.out.println("Room successfully booked: " + roomType);
        System.out.println("Remaining " + roomType + " rooms: " + inventory.get(roomType));
    }

    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (String room : inventory.keySet()) {
            System.out.println(room + " : " + inventory.get(room));
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        RoomInventory inventory = new RoomInventory();

        System.out.println("=== Book My Stay App ===");

        inventory.displayInventory();

        try {
            System.out.print("\nEnter room type to book (Standard / Deluxe / Suite): ");
            String roomType = sc.nextLine();

            // Attempt booking with validation
            inventory.bookRoom(roomType);
        }

        // Graceful error handling
        catch (InvalidBookingException e) {
            System.out.println("Booking Failed: " + e.getMessage());
        }

        // Catch unexpected errors
        catch (Exception e) {
            System.out.println("Unexpected error occurred.");
        }

        System.out.println("\nSystem continues running safely.");

        sc.close();
    }
}