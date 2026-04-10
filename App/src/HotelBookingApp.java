package com.hotel.uc7;

/**
 * Represents an optional add-on service available for a reservation.
 *
 * @author Prabhu Nagamani
 * @version 1.0
 */
public class AddOnService {

    private final String name;
    private final double cost;

    public AddOnService(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() { return name; }
    public double getCost() { return cost; }

    @Override
    public String toString() {
        return String.format("Service[name=%s, cost=$%.2f]", name, cost);
    }
}
package com.hotel.uc7;

/**
 * Demonstrates UC7: Add-On Service Selection.
 *
 * @author Prabhu Nagamani
 * @version 1.0
 */
public class AddOnServiceDemo {

    public static void main(String[] args) {
        AddOnServiceManager manager = new AddOnServiceManager();

        // Predefined catalogue of available services
        AddOnService breakfast   = new AddOnService("Breakfast Buffet", 25.0);
        AddOnService airportDrop = new AddOnService("Airport Drop",     40.0);
        AddOnService spa         = new AddOnService("Spa Session",      80.0);
        AddOnService laundry     = new AddOnService("Laundry Service",  15.0);

        System.out.println("===== Guest Selecting Add-On Services =====");

        // Reservation CONF-1001 selects multiple services
        manager.addService("CONF-1001", breakfast);
        manager.addService("CONF-1001", spa);
        manager.addService("CONF-1001", laundry);

        // Reservation CONF-1002 selects one service
        manager.addService("CONF-1002", airportDrop);

        // Display summary for each reservation
        manager.displayServicesForReservation("CONF-1001");
        manager.displayServicesForReservation("CONF-1002");
        manager.displayServicesForReservation("CONF-1003"); // no services
    }
}

package com.hotel.uc7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages the association between reservations and their selected add-on services.
 * <p>
 * Uses a Map&lt;String, List&lt;AddOnService&gt;&gt; to model the one-to-many
 * relationship between a reservation ID and its services. Core booking
 * and inventory state are never modified by this component.
 * </p>
 *
 * @author Prabhu Nagamani
 * @version 1.0
 */
public class AddOnServiceManager {

    private final Map<String, List<AddOnService>> reservationServices;

    public AddOnServiceManager() {
        reservationServices = new HashMap<>();
    }

    /**
     * Attaches an add-on service to the specified reservation.
     *
     * @param reservationId the unique confirmation ID
     * @param service       the service to add
     */
    public void addService(String reservationId, AddOnService service) {
        reservationServices
                .computeIfAbsent(reservationId, k -> new ArrayList<>())
                .add(service);
        System.out.println("Added " + service.getName()
                + " to reservation " + reservationId);
    }

    /**
     * Returns all services attached to the given reservation.
     *
     * @param reservationId the reservation to query
     * @return list of services, or empty list if none
     */
    public List<AddOnService> getServices(String reservationId) {
        return reservationServices.getOrDefault(reservationId, new ArrayList<>());
    }

    /**
     * Calculates the total additional cost for a reservation's services.
     *
     * @param reservationId the reservation to total
     * @return sum of all service costs
     */
    public double calculateTotalAddOnCost(String reservationId) {
        return getServices(reservationId)
                .stream()
                .mapToDouble(AddOnService::getCost)
                .sum();
    }

    /**
     * Displays all services and their total cost for a reservation.
     *
     * @param reservationId the reservation to display
     */
    public void displayServicesForReservation(String reservationId) {
        List<AddOnService> services = getServices(reservationId);
        System.out.println("\n===== Add-On Services for " + reservationId + " =====");
        if (services.isEmpty()) {
            System.out.println("  No add-on services selected.");
        } else {
            for (AddOnService s : services) {
                System.out.printf("  %-20s : $%.2f%n", s.getName(), s.getCost());
            }
            System.out.printf("  %-20s : $%.2f%n",
                    "Total Add-On Cost", calculateTotalAddOnCost(reservationId));
        }
        System.out.println("Core booking and inventory state unchanged.");
    }
}

