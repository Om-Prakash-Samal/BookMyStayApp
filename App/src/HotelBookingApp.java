package com.hotel.uc2;

/**
 * Represents a Double Room with two beds and enhanced amenities.
 *
 * @author Niranjan Manivannan
 * @version 1.0
 */
public class DoubleRoom extends Room {

    public DoubleRoom() {
        super("Double Room", 2, 350.0, 140.0);
    }

    @Override
    public String getAmenities() {
        return "Wi-Fi, TV, Air Conditioning, Mini Bar";
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Amenities      : " + getAmenities());
    }
}

package com.hotel.uc2;

/**
 * Abstract base class representing a generic hotel room.
 * <p>
 * Defines shared attributes and enforces a consistent structure
 * across all concrete room types via inheritance.
 * </p>
 *
 * @author Niranjan Manivannan
 * @version 1.0
 */
public abstract class Room {

    private String roomType;
    private int numberOfBeds;
    private double sizeInSqFt;
    private double pricePerNight;

    /**
     * Constructs a Room with the specified attributes.
     *
     * @param roomType       the category of the room
     * @param numberOfBeds   number of beds in the room
     * @param sizeInSqFt     floor area in square feet
     * @param pricePerNight  nightly rate in USD
     */
    public Room(String roomType, int numberOfBeds, double sizeInSqFt, double pricePerNight) {
        this.roomType = roomType;
        this.numberOfBeds = numberOfBeds;
        this.sizeInSqFt = sizeInSqFt;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomType()      { return roomType; }
    public int getNumberOfBeds()     { return numberOfBeds; }
    public double getSizeInSqFt()    { return sizeInSqFt; }
    public double getPricePerNight() { return pricePerNight; }

    /**
     * Displays the room details to the console.
     * Subclasses may override to add type-specific information.
     */
    public void displayDetails() {
        System.out.println("----------------------------------");
        System.out.println("Room Type      : " + roomType);
        System.out.println("Number of Beds : " + numberOfBeds);
        System.out.println("Size (sq ft)   : " + sizeInSqFt);
        System.out.println("Price/Night    : $" + pricePerNight);
    }

    /**
     * Returns a description of the room's amenities.
     * Must be implemented by each concrete subclass.
     *
     * @return amenities description string
     */
    public abstract String getAmenities();
}

package com.hotel.uc2;

/**
 * Demonstrates room type modeling with static availability variables.
 * <p>
 * Highlights the limitation of using scattered variables to represent
 * availability state, which is resolved in UC3 using a HashMap.
 * </p>
 *
 * @author Niranjan Manivannan
 * @version 1.0
 */
public class RoomTypeDemo {

    public static void main(String[] args) {
        // Static availability — intentionally scattered to show the limitation
        int singleRoomAvailable = 5;
        int doubleRoomAvailable = 3;
        int suiteRoomAvailable  = 2;

        // Polymorphic references to room objects
        Room single = new SingleRoom();
        Room doublr = new DoubleRoom();
        Room suite  = new SuiteRoom();

        System.out.println("========== Room Types & Availability ==========");

        single.displayDetails();
        System.out.println("Available      : " + singleRoomAvailable);

        doublr.displayDetails();
        System.out.println("Available      : " + doubleRoomAvailable);

        suite.displayDetails();
        System.out.println("Available      : " + suiteRoomAvailable);

        System.out.println("==============================================");
        System.out.println("Note: Availability stored in separate variables.");
        System.out.println("This will be improved with a centralized HashMap in UC3.");
    }
}

package com.hotel.uc2;

/**
 * Represents a Single Room with one bed and basic amenities.
 *
 * @author Niranjan Manivannan
 * @version 1.0
 */
public class SingleRoom extends Room {

    public SingleRoom() {
        super("Single Room", 1, 200.0, 80.0);
    }

    @Override
    public String getAmenities() {
        return "Wi-Fi, TV, Air Conditioning";
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Amenities      : " + getAmenities());
    }
}

package com.hotel.uc2;

/**
 * Represents a Suite Room with premium amenities and a separate living area.
 *
 * @author Niranjan Manivannan
 * @version 1.0
 */
public class SuiteRoom extends Room {

    public SuiteRoom() {
        super("Suite Room", 2, 700.0, 350.0);
    }

    @Override
    public String getAmenities() {
        return "Wi-Fi, Smart TV, Air Conditioning, Mini Bar, Jacuzzi, Living Area";
    }

    @Override
    public void displayDetails() {
        super.displayDetails();
        System.out.println("Amenities      : " + getAmenities());
    }
}

