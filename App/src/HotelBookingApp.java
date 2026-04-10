package com.hotel.uc5;

/**
 * Demonstrates UC5: Booking Request Queue using FIFO ordering.
 *
 * @author Niranjan Manivannan
 * @version 1.0
 */
public class BookingQueueDemo {

    public static void main(String[] args) {
        BookingRequestQueue queue = new BookingRequestQueue();

        System.out.println("===== Guests Submitting Booking Requests =====");
        queue.addRequest(new Reservation("Alice", "Single Room", 2));
        queue.addRequest(new Reservation("Bob",   "Double Room", 3));
        queue.addRequest(new Reservation("Carol",  "Suite Room",  1));
        queue.addRequest(new Reservation("David", "Single Room", 5));

        System.out.println();
        queue.displayQueue();

        System.out.println();
        System.out.println("Next to be processed (peek): " + queue.peekRequest());
        System.out.println("No inventory changes have occurred at this stage.");
    }
}

package com.hotel.uc5;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Manages incoming booking requests using a FIFO queue.
 * <p>
 * Ensures fair ordering of requests without performing any room allocation
 * or modifying inventory state at this stage.
 * </p>
 *
 * @author Niranjan Manivannan
 * @version 1.0
 */
public class BookingRequestQueue {

    private final Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    /**
     * Adds a booking request to the end of the queue.
     *
     * @param reservation the guest's booking request
     */
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Request queued: " + reservation);
    }

    /**
     * Retrieves and removes the next request from the queue (FIFO).
     *
     * @return the earliest pending reservation, or null if queue is empty
     */
    public Reservation pollRequest() {
        return requestQueue.poll();
    }

    /**
     * Peeks at the next request without removing it.
     *
     * @return the next pending reservation, or null if queue is empty
     */
    public Reservation peekRequest() {
        return requestQueue.peek();
    }

    public boolean isEmpty() { return requestQueue.isEmpty(); }
    public int     size()    { return requestQueue.size(); }

    /**
     * Displays all pending requests in queue order.
     */
    public void displayQueue() {
        System.out.println("---- Pending Booking Requests (" + size() + " total) ----");
        int position = 1;
        for (Reservation r : requestQueue) {
            System.out.println("  " + position++ + ". " + r);
        }
    }
}

package com.hotel.uc5;

/**
 * Represents a guest's booking request (intent to reserve a room).
 *
 * @author Niranjan Manivannan
 * @version 1.0
 */
public class Reservation {

    private final String guestName;
    private final String roomType;
    private final int    nights;

    public Reservation(String guestName, String roomType, int nights) {
        this.guestName = guestName;
        this.roomType  = roomType;
        this.nights    = nights;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType()  { return roomType; }
    public int    getNights()    { return nights; }

    @Override
    public String toString() {
        return String.format("Reservation[guest=%s, roomType=%s, nights=%d]",
                guestName, roomType, nights);
    }
}

