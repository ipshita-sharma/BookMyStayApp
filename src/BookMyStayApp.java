// File: UseCase4RoomSearch.java

import java.util.*;

// Domain model for a Room
class Room {
    private String type;
    private double pricePerNight;
    private List<String> amenities;

    public Room(String type, double pricePerNight, List<String> amenities) {
        this.type = type;
        this.pricePerNight = pricePerNight;
        this.amenities = new ArrayList<>(amenities); // defensive copy
    }

    public String getType() {
        return type;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public List<String> getAmenities() {
        return Collections.unmodifiableList(amenities);
    }

    @Override
    public String toString() {
        return "Room Type: " + type + "\nPrice: $" + pricePerNight +
                "\nAmenities: " + String.join(", ", amenities);
    }
}

// Inventory class to hold room availability
class Inventory {
    private Map<String, Integer> roomAvailability; // Room type -> count
    private Map<String, Room> rooms; // Room type -> Room object

    public Inventory() {
        roomAvailability = new HashMap<>();
        rooms = new HashMap<>();
    }

    public void addRoom(Room room, int count) {
        rooms.put(room.getType(), room);
        roomAvailability.put(room.getType(), count);
    }

    // Read-only access to availability
    public int getAvailability(String roomType) {
        return roomAvailability.getOrDefault(roomType, 0);
    }

    public Room getRoom(String roomType) {
        return rooms.get(roomType);
    }

    public Set<String> getAllRoomTypes() {
        return rooms.keySet();
    }
}

// Service class for searching rooms
class SearchService {
    private Inventory inventory;

    public SearchService(Inventory inventory) {
        this.inventory = inventory;
    }

    public void displayAvailableRooms() {
        boolean found = false;
        for (String roomType : inventory.getAllRoomTypes()) {
            int available = inventory.getAvailability(roomType);
            if (available > 0) { // Only show available rooms
                found = true;
                Room room = inventory.getRoom(roomType);
                System.out.println("----------------------------");
                System.out.println(room);
                System.out.println("Available Rooms: " + available);
            }
        }
        if (!found) {
            System.out.println("No rooms available at the moment.");
        }
    }
}

// Actor class representing Guest
class Guest {
    private SearchService searchService;

    public Guest(SearchService searchService) {
        this.searchService = searchService;
    }

    public void searchRooms() {
        System.out.println("Searching for available rooms...\n");
        searchService.displayAvailableRooms();
    }
}

// Main class to run Use Case 4
public class BookMyStayApp {
    public static void main(String[] args) {
        // Setup inventory
        Inventory inventory = new Inventory();
        inventory.addRoom(new Room("Single", 100.0, Arrays.asList("WiFi", "TV")), 5);
        inventory.addRoom(new Room("Double", 180.0, Arrays.asList("WiFi", "TV", "Mini Bar")), 2);
        inventory.addRoom(new Room("Suite", 350.0, Arrays.asList("WiFi", "TV", "Mini Bar", "Jacuzzi")), 0); // unavailable

        // Initialize search service
        SearchService searchService = new SearchService(inventory);

        // Guest initiates search
        Guest guest = new Guest(searchService);
        guest.searchRooms();
    }
}