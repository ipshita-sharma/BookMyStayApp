import java.util.*;

// Booking Request Class
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Booking Queue
class BookingQueue {

    Queue<BookingRequest> queue = new LinkedList<>();

    public synchronized void addRequest(BookingRequest request) {
        queue.add(request);
        System.out.println(request.guestName + " submitted booking for " + request.roomType);
    }

    public synchronized BookingRequest getRequest() {
        return queue.poll();
    }
}

// Inventory Manager (Shared Resource)
class RoomInventory {

    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Standard", 2);
        inventory.put("Deluxe", 1);
        inventory.put("Suite", 1);
    }

    // Critical Section
    public synchronized boolean allocateRoom(String roomType, String guestName) {

        if (!inventory.containsKey(roomType)) {
            System.out.println("Invalid room type requested by " + guestName);
            return false;
        }

        int available = inventory.get(roomType);

        if (available <= 0) {
            System.out.println("No " + roomType + " rooms available for " + guestName);
            return false;
        }

        inventory.put(roomType, available - 1);

        System.out.println("Room allocated: " + roomType + " for " + guestName +
                " | Remaining: " + inventory.get(roomType));

        return true;
    }

    public void displayInventory() {
        System.out.println("\nFinal Inventory State:");
        for (String room : inventory.keySet()) {
            System.out.println(room + " : " + inventory.get(room));
        }
    }
}

// Booking Processor Thread
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue queue, RoomInventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    public void run() {

        while (true) {

            BookingRequest request;

            synchronized (queue) {
                request = queue.getRequest();
            }

            if (request == null)
                break;

            inventory.allocateRoom(request.roomType, request.guestName);

            try {
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) {

        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();

        // Multiple guests submit requests
        queue.addRequest(new BookingRequest("Alice", "Standard"));
        queue.addRequest(new BookingRequest("Bob", "Standard"));
        queue.addRequest(new BookingRequest("Charlie", "Deluxe"));
        queue.addRequest(new BookingRequest("David", "Suite"));
        queue.addRequest(new BookingRequest("Eva", "Deluxe"));

        // Multiple processor threads
        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);
        BookingProcessor t3 = new BookingProcessor(queue, inventory);

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (Exception e) {
        }

        inventory.displayInventory();
    }
}