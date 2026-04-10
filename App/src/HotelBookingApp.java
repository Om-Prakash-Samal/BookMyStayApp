package com.hotel.uc4;

/**
 * Room domain model used by the search service to display room details.
 *
 * @author Niranjan Manivannan
 * @version 1.0
 */
public abstract class Room {
    private String roomType;
    private int numberOfBeds;
    private double sizeInSqFt;
    private double pricePerNight;

    public Room(String roomType, int numberOfBeds, double sizeInSqFt, double pricePerNight) {
        this.roomType      = roomType;
        this.numberOfBeds  = numberOfBeds;
        this.sizeInSqFt    = sizeInSqFt;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomType()      { return roomType; }
    public int getNumberOfBeds()     { return numberOfBeds; }
    public double getSizeInSqFt()    { return sizeInSqFt; }
    public double getPricePerNight() { return pricePerNight; }

    public abstract String getAmenities();
}
package com.hotel.uc4;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides read-only search capabilities over the room inventory.
 * <p>
 * Ensures that searching for available rooms never modifies inventory state.
 * Only room types with availability greater than zero are displayed.
 * </p>
 *
 * @author Niranjan Manivannan
 * @version 1.0
 */
public class RoomSearchService {

    // Simulated inventory (read-only access during search)
    private final Map<String, Integer> inventory;
    private final Map<String, Room>    roomCatalog;

    public RoomSearchService() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // intentionally unavailable
        inventory.put("Suite Room",  2);

        roomCatalog = new HashMap<>();
        roomCatalog.put("Single Room", new SingleRoom());
        roomCatalog.put("Double Room", new DoubleRoom());
        roomCatalog.put("Suite Room",  new SuiteRoom());
    }

    /**
     * Displays all room types that currently have availability greater than zero.
     * Inventory state is never modified during this operation.
     */
    public void searchAvailableRooms() {
        System.out.println("========== Available Rooms ==========");
        boolean anyFound = false;

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            String roomType = entry.getKey();
            int    count    = entry.getValue();

            // Validation: only show rooms with availability > 0
            if (count > 0) {
                anyFound = true;
                Room room = roomCatalog.get(roomType);
                System.out.println("Room Type    : " + room.getRoomType());
                System.out.println("Beds         : " + room.getNumberOfBeds());
                System.out.println("Size (sq ft) : " + room.getSizeInSqFt());
                System.out.println("Price/Night  : $" + room.getPricePerNight());
                System.out.println("Amenities    : " + room.getAmenities());
                System.out.println("Available    : " + count);
                System.out.println("-------------------------------------");
            }
        }

        if (!anyFound) {
            System.out.println("No rooms are currently available.");
        }
        System.out.println("Inventory state unchanged after search.");
    }
}

package com.hotel.uc4;

class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 1, 200.0, 80.0); }
    @Override public String getAmenities() { return "Wi-Fi, TV, Air Conditioning"; }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 2, 350.0, 140.0); }
    @Override public String getAmenities() { return "Wi-Fi, TV, Air Conditioning, Mini Bar"; }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 2, 700.0, 350.0); }
    @Override public String getAmenities() { return "Wi-Fi, Smart TV, Air Conditioning, Mini Bar, Jacuzzi, Living Area"; }
}

package com.hotel.uc4;

/**
 * Demonstrates UC4: Room Search and Availability Check.
 *
 * @author Niranjan Manivannan
 * @version 1.0
 */
public class SearchDemo {

    public static void main(String[] args) {
        RoomSearchService searchService = new RoomSearchService();
        System.out.println("Guest initiates a room search...");
        searchService.searchAvailableRooms();
    }
}

