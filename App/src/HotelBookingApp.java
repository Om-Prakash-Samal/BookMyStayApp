package com.hotel.uc1;

/**
 * HotelBookingApp serves as the entry point for the Hotel Booking System.
 * <p>
 * This class demonstrates the fundamental structure of a Java application,
 * including the use of a class as a container, the main() method as the
 * JVM entry point, and console output via System.out.println().
 * </p>
 *
 * @author Niranjan Manivannan
 * @version 1.0
 */
public class HotelBookingApp {

    /**
     * The main method is the entry point of the Hotel Booking System.
     * <p>
     * The JVM invokes this method directly when the application starts.
     * Execution proceeds linearly from top to bottom, printing a welcome
     * message and version information before the application terminates.
     * </p>
     *
     * @param args command-line arguments (not used in this use case)
     */
    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("   Welcome to Hotel Booking System      ");
        System.out.println("   Application Name : Hotel Booking System");
        System.out.println("   Version          : v1.0               ");
        System.out.println("========================================");
        System.out.println("Application started successfully.");
        System.out.println("Application terminated.");
    }
}
