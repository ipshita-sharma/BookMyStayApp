// Version: 2.1

// Abstract Room Class
abstract class Room {

    private String roomType;
    private int numberOfBeds;
    private double size;
    private double price;

    public Room(String roomType, int numberOfBeds, double size, double price) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getNumberOfBeds() {
        return numberOfBeds;
    }

    public double getSize() {
        return size;
    }

    public double getPrice() {
        return price;
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price per night: $" + price);
    }
}

// Single Room Class (Version 2.0)
class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200, 100);
    }
}

// Double Room Class (Version 2.0)
class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350, 180);
    }
}

// Suite Room Class (Version 2.0)
class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 3, 600, 350);
    }
}

// Main Application Class
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("=== Book My Stay - Room Availability ===");

        // Polymorphism
        Room singleRoom = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suiteRoom = new SuiteRoom();

        // Static Availability Variables
        int singleAvailability = 5;
        int doubleAvailability = 3;
        int suiteAvailability = 2;

        // Display Details
        System.out.println("\n--- Single Room ---");
        singleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + singleAvailability);

        System.out.println("\n--- Double Room ---");
        doubleRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + doubleAvailability);

        System.out.println("\n--- Suite Room ---");
        suiteRoom.displayRoomDetails();
        System.out.println("Available Rooms: " + suiteAvailability);

        System.out.println("\nApplication Terminated.");
    }
}