import java.util.*;

// Booking request class
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Inventory Service to maintain room availability
class InventoryService {
    private Map<String, Integer> roomInventory;

    public InventoryService(Map<String, Integer> initialInventory) {
        this.roomInventory = new HashMap<>(initialInventory);
    }

    // Check if a room type is available
    public boolean isAvailable(String roomType) {
        return roomInventory.getOrDefault(roomType, 0) > 0;
    }

    // Decrement inventory after successful allocation
    public void decrementInventory(String roomType) {
        roomInventory.put(roomType, roomInventory.get(roomType) - 1);
    }

    public void printInventory() {
        System.out.println("Current Inventory: " + roomInventory);
    }
}

// Booking Service to process requests and allocate rooms
class BookingService {
    private Queue<BookingRequest> requestQueue;
    private InventoryService inventoryService;
    private Map<String, Set<String>> allocatedRooms; // roomType -> allocated room IDs
    private int roomCounter = 100; // to generate unique room IDs

    public BookingService(InventoryService inventoryService) {
        this.requestQueue = new LinkedList<>();
        this.inventoryService = inventoryService;
        this.allocatedRooms = new HashMap<>();
    }

    // Add a booking request to the queue
    public void addBookingRequest(BookingRequest request) {
        requestQueue.offer(request);
    }

    // Process booking requests
    public void processBookings() {
        while (!requestQueue.isEmpty()) {
            BookingRequest request = requestQueue.poll();
            String roomType = request.roomType;

            if (inventoryService.isAvailable(roomType)) {
                String roomId = generateUniqueRoomId(roomType);

                // Assign room
                allocatedRooms.computeIfAbsent(roomType, k -> new HashSet<>()).add(roomId);

                // Update inventory
                inventoryService.decrementInventory(roomType);

                System.out.println("Reservation confirmed for " + request.guestName +
                        ". Room Type: " + roomType + ", Room ID: " + roomId);
            } else {
                System.out.println("No rooms available for " + request.guestName +
                        ". Requested Room Type: " + roomType);
            }
        }
    }

    // Generate a unique room ID
    private String generateUniqueRoomId(String roomType) {
        String roomId;
        do {
            roomId = roomType.substring(0, 1).toUpperCase() + roomCounter++;
        } while (allocatedRooms.getOrDefault(roomType, new HashSet<>()).contains(roomId));
        return roomId;
    }

    // Print all allocated rooms
    public void printAllocatedRooms() {
        System.out.println("Allocated Rooms: " + allocatedRooms);
    }
}

// Main class
public class BookMyStayApp {
    public static void main(String[] args) {
        // Initial inventory setup
        Map<String, Integer> initialInventory = new HashMap<>();
        initialInventory.put("Single", 3);
        initialInventory.put("Double", 2);
        initialInventory.put("Suite", 1);

        InventoryService inventoryService = new InventoryService(initialInventory);
        BookingService bookingService = new BookingService(inventoryService);

        // Add booking requests
        bookingService.addBookingRequest(new BookingRequest("Alice", "Single"));
        bookingService.addBookingRequest(new BookingRequest("Bob", "Double"));
        bookingService.addBookingRequest(new BookingRequest("Charlie", "Suite"));
        bookingService.addBookingRequest(new BookingRequest("David", "Single"));
        bookingService.addBookingRequest(new BookingRequest("Eve", "Double"));
        bookingService.addBookingRequest(new BookingRequest("Frank", "Single")); // Should fail if inventory exhausted

        // Process bookings
        bookingService.processBookings();

        // Print final state
        bookingService.printAllocatedRooms();
        inventoryService.printInventory();
    }
}