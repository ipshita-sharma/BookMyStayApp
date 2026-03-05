import java.util.HashMap;
import java.util.Map;

class RoomInventory {

    // Centralized HashMap to store room availability
    private HashMap<String, Integer> inventory;

    // Constructor initializes inventory
    public RoomInventory() {
        inventory = new HashMap<>();
    }

    // Register room type with available count
    public void registerRoomType(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Get availability of a specific room type
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability in a controlled way
    public void updateAvailability(String roomType, int newCount) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, newCount);
        } else {
            System.out.println("Room type not found in inventory.");
        }
    }

    // Display current inventory
    public void displayInventory() {
        System.out.println("Current Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue() + " rooms available");
        }
    }
}

public class BookMyStayApp {

    public static void main(String[] args) {

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Register room types
        inventory.registerRoomType("Standard", 10);
        inventory.registerRoomType("Deluxe", 5);
        inventory.registerRoomType("Suite", 2);

        // Display inventory
        inventory.displayInventory();

        System.out.println();

        // Retrieve availability
        System.out.println("Available Deluxe Rooms: " + inventory.getAvailability("Deluxe"));

        System.out.println();

        // Update availability
        inventory.updateAvailability("Deluxe", 4);

        // Display updated inventory
        inventory.displayInventory();
    }
}