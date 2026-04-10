package com.hotel.uc3;

/**
 * Demonstrates centralized room inventory management using HashMap.
 *
 * @author Niranjan Manivannan
 * @version 1.0
 */
public class InventoryDemo {

    public static void main(String[] args) {
        RoomInventory inventory = new RoomInventory();

        System.out.println("Initial inventory:");
        inventory.displayInventory();

        System.out.println("Booking one Single Room...");
        inventory.decrementAvailability("Single Room");
        inventory.displayInventory();

        System.out.println("Booking two Suite Rooms...");
        inventory.decrementAvailability("Suite Room");
        inventory.decrementAvailability("Suite Room");
        inventory.displayInventory();

        System.out.println("Availability of Double Room: "
                + inventory.getAvailability("Double Room"));
    }
}

package com.hotel.uc3;

import java.util.HashMap;
import java.util.Map;

/**
 * Centralized inventory manager for hotel room availability.
 * <p>
 * Uses a HashMap to store room type availability counts, providing
 * O(1) lookups and a single source of truth for all availability state.
 * </p>
 *
 * @author Niranjan Manivannan
 * @version 1.0
 */
public class RoomInventory {

    private final Map<String, Integer> availabilityMap;

    /**
     * Initializes the inventory with default room type counts.
     */
    public RoomInventory() {
        availabilityMap = new HashMap<>();
        availabilityMap.put("Single Room", 5);
        availabilityMap.put("Double Room", 3);
        availabilityMap.put("Suite Room",  2);
    }

    /**
     * Returns the available count for the specified room type.
     *
     * @param roomType the room category to query
     * @return available count, or 0 if room type is not registered
     */
    public int getAvailability(String roomType) {
        return availabilityMap.getOrDefault(roomType, 0);
    }

    /**
     * Decrements availability by one for the given room type.
     *
     * @param roomType the room category to update
     */
    public void decrementAvailability(String roomType) {
        int current = getAvailability(roomType);
        if (current > 0) {
            availabilityMap.put(roomType, current - 1);
        }
    }

    /**
     * Increments availability by one for the given room type (used during rollback).
     *
     * @param roomType the room category to restore
     */
    public void incrementAvailability(String roomType) {
        availabilityMap.put(roomType, getAvailability(roomType) + 1);
    }

    /**
     * Displays the current inventory state to the console.
     */
    public void displayInventory() {
        System.out.println("========== Current Room Inventory ==========");
        for (Map.Entry<String, Integer> entry : availabilityMap.entrySet()) {
            System.out.printf("  %-15s : %d available%n", entry.getKey(), entry.getValue());
        }
        System.out.println("============================================");
    }

    /**
     * Returns the full availability map for read-only operations.
     *
     * @return unmodifiable view of the availability map
     */
    public Map<String, Integer> getAllAvailability() {
        return java.util.Collections.unmodifiableMap(availabilityMap);
    }
}
