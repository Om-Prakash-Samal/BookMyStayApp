package com.hotel.uc6;

/**
 * Demonstrates UC6: Reservation Confirmation and Room Allocation.
 *
 * @author Niranjan Manivannan
 * @version 1.0
 */
public class AllocationDemo {

    public static void main(String[] args) {
        BookingService service = new BookingService();

        System.out.println("===== Submitting Booking Requests =====");
        service.submitRequest(new Reservation("Alice", "Single Room", 2));
        service.submitRequest(new Reservation("Bob",   "Suite Room",  1));
        service.submitRequest(new Reservation("Carol",  "Double Room", 3));
        service.submitRequest(new Reservation("David", "Suite Room",  2));
        service.submitRequest(new Reservation("Eve",   "Suite Room",  1)); // should be declined

        service.processAllRequests();
        service.displayStatus();
    }
}

package com.hotel.uc6;

import java.util.*;

/**
 * Processes booking requests and allocates unique room IDs.
 * <p>
 * Uses a Set to enforce room ID uniqueness and a HashMap to group
 * allocated IDs by room type. Inventory is updated atomically with allocation.
 * </p>
 *
 * @author Niranjan Manivannan
 * @version 1.0
 */
public class BookingService {

    private final Map<String, Integer>    inventory;
    private final Set<String>             allocatedRoomIds;
    private final Map<String, Set<String>> roomTypeAllocations;
    private final Queue<Reservation>      requestQueue;
    private int                           confirmationCounter = 1000;

    public BookingService() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room",  2);

        allocatedRoomIds    = new HashSet<>();
        roomTypeAllocations = new HashMap<>();
        requestQueue        = new LinkedList<>();
    }

    /** Adds a booking request to the queue. */
    public void submitRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Queued: " + reservation.getGuestName()
                + " -> " + reservation.getRoomType());
    }

    /**
     * Processes all queued requests in FIFO order.
     * Allocates a unique room ID and updates inventory atomically.
     */
    public void processAllRequests() {
        System.out.println("\n===== Processing Booking Requests =====");
        while (!requestQueue.isEmpty()) {
            Reservation res = requestQueue.poll();
            processRequest(res);
        }
    }

    private void processRequest(Reservation res) {
        String roomType = res.getRoomType();
        int available   = inventory.getOrDefault(roomType, 0);

        if (available <= 0) {
            System.out.println("DECLINED: No availability for " + roomType
                    + " (guest: " + res.getGuestName() + ")");
            return;
        }

        // Generate unique room ID
        String roomId = generateUniqueRoomId(roomType);

        // Record allocation — Set enforces uniqueness
        allocatedRoomIds.add(roomId);
        roomTypeAllocations
                .computeIfAbsent(roomType, k -> new HashSet<>())
                .add(roomId);

        // Decrement inventory atomically
        inventory.put(roomType, available - 1);

        // Assign confirmation
        String confId = "CONF-" + (++confirmationCounter);
        res.setConfirmationId(confId);
        res.setAllocatedRoomId(roomId);

        System.out.println("CONFIRMED: " + res);
    }

    private String generateUniqueRoomId(String roomType) {
        String prefix = roomType.replaceAll("[^A-Z]", "");
        String candidate;
        int counter = 1;
        do {
            candidate = prefix + String.format("%03d", counter++);
        } while (allocatedRoomIds.contains(candidate));
        return candidate;
    }

    /** Displays the current state of inventory and allocations. */
    public void displayStatus() {
        System.out.println("\n--- Remaining Inventory ---");
        inventory.forEach((k, v) -> System.out.printf("  %-15s : %d%n", k, v));

        System.out.println("--- Allocated Room IDs ---");
        roomTypeAllocations.forEach((type, ids) ->
                System.out.println("  " + type + " : " + ids));
    }
}

package com.hotel.uc6;

/**
 * Represents a guest booking request with a unique confirmation ID once allocated.
 *
 * @author Niranjan Manivannan
 * @version 1.0
 */
public class Reservation {

    private final String guestName;
    private final String roomType;
    private final int    nights;
    private String confirmationId;
    private String allocatedRoomId;

    public Reservation(String guestName, String roomType, int nights) {
        this.guestName = guestName;
        this.roomType  = roomType;
        this.nights    = nights;
    }

    public String getGuestName()    { return guestName; }
    public String getRoomType()     { return roomType; }
    public int    getNights()       { return nights; }

    public String getConfirmationId()  { return confirmationId; }
    public void   setConfirmationId(String id) { this.confirmationId = id; }

    public String getAllocatedRoomId()   { return allocatedRoomId; }
    public void   setAllocatedRoomId(String id) { this.allocatedRoomId = id; }

    @Override
    public String toString() {
        return String.format("Reservation[conf=%s, guest=%s, room=%s, allocated=%s, nights=%d]",
                confirmationId, guestName, roomType, allocatedRoomId, nights);
    }
}
