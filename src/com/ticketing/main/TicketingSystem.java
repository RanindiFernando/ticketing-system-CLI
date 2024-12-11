package com.ticketing.main;

import com.ticketing.config.Configuration;
import com.ticketing.model.TicketPool;
import com.ticketing.producer.Vendor;
import com.ticketing.consumer.Customer;
import com.ticketing.util.JSONFileWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for the Real-Time Ticketing System.
 * Handles simulation setup, thread management, and persistence.
 */
public class TicketingSystem {
    public static void main(String[] args) {
        // Initialize configuration
        Configuration config = new Configuration();

        // Configure system: Ask whether to load existing configuration or enter a new one
        config.configureSystem();  // This will ask the user and configure accordingly

        // Initialize ticket pool
        TicketPool ticketPool = new TicketPool(config.getTotalTickets(), config.getMaxTicketCapacity());

        // Thread lists for vendors and customers
        List<Thread> vendorThreads = new ArrayList<>();
        List<Thread> customerThreads = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        boolean simulationRunning = false;

        // Loop for the menu until the simulation is stopped
        while (true) {
            if (!simulationRunning) {
                // Only show this menu if the simulation is not running
                System.out.println("1. Start Simulation");
                System.out.println("2. Exit Simulation");
                System.out.print("Enter your choice: ");
            }

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    if (simulationRunning) {
                        System.out.println("Simulation is already running.");
                        break;
                    }

                    // Start vendor threads
                    createThreads(vendorThreads, 5, i -> {
                        String vendorName = "Vendor-" + i;
                        Vendor vendor = new Vendor(ticketPool, config.getTicketReleaseRate(), vendorName);
                        return new Thread(vendor);
                    });

                    // Start customer threads
                    createThreads(customerThreads, 3, i -> {
                        String customerName = "Customer-" + i;
                        Customer customer = new Customer(ticketPool, config.getCustomerRetrievalRate(), customerName);
                        return new Thread(customer);
                    });

                    simulationRunning = true;
                    System.out.println("Simulation started with 5 Vendors and 3 Customers.\n");
                    break;

                case "2":
                    if (!simulationRunning) {
                        System.out.println("No simulation is running. Exiting application.");
                        return;
                    }

                    // Stop simulation
                    stopThreads(vendorThreads, "Vendor");
                    stopThreads(customerThreads, "Customer");

                    // Write transactions to file (or any other data you need to save)
                    System.out.println("Saving transaction data...");
                    try {
                        // Assuming you want to save ticket transactions to a file
                        JSONFileWriter.writeTicketRecordsToJSON(ticketPool.getTransactions(), "transactions.json");
                        System.out.println("Transactions saved to transactions.json.");
                    } catch (Exception e) {
                        System.err.println("Failed to save transactions: " + e.getMessage());
                    }

                    simulationRunning = false;
                    return;

                default:
                    System.out.println("Invalid choice. Please select 1 or 2.");
            }
        }
    }

    /**
     * Creates and starts threads for entities (e.g., vendors and customers).
     *
     * @param threads  List to store threads.
     * @param count    Number of threads to create.
     * @param factory  Function to generate threads.
     */
    private static void createThreads(List<Thread> threads, int count, ThreadFactory factory) {
        for (int i = 1; i <= count; i++) {
            Thread thread = factory.createThread(i);
            threads.add(thread);
            thread.start();
        }
    }

    /**
     * Stops threads by interrupting them and waiting for completion.
     *
     * @param threads List of threads to stop.
     * @param entity  Entity type (Vendor or Customer) for messages.
     */
    private static void stopThreads(List<Thread> threads, String entity) {
        System.out.println("Stopping " + entity + " threads...");
        for (Thread thread : threads) {
            thread.interrupt();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println(entity + " thread interrupted during join.");
            }
        }
        threads.clear();
        System.out.println(entity + " threads stopped.");
    }

    /**
     * Functional interface for creating threads.
     */
    @FunctionalInterface
    private interface ThreadFactory {
        Thread createThread(int index);
    }
}
