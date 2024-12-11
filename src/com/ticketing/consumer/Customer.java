package com.ticketing.consumer;

import com.ticketing.model.TicketPool;

/**
 * Represents a customer thread that purchases tickets at regular intervals.
 */
public class Customer implements Runnable {
    private final TicketPool ticketPool;
    private final int retrievalInterval;
    private final String customerName;
    private boolean isRunning = true; // Flag to control thread execution

    /**
     * Initializes a Customer object.
     *
     * @param ticketPool       The shared ticket pool object.
     * @param retrievalInterval Time interval (milliseconds) between purchases.
     * @param customerName     Name of the customer.
     */
    public Customer(TicketPool ticketPool, int retrievalInterval, String customerName) {
        this.ticketPool = ticketPool;
        this.retrievalInterval = retrievalInterval;
        this.customerName = customerName;
    }

    /**
     * Stops the customer thread.
     */
    public void stopCustomer() {
        isRunning = false;
    }

    /**
     * Main logic for the customer thread. Periodically attempts to buy tickets.
     */
    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(retrievalInterval); // Wait for the next purchase attempt
                int ticketsToBuy = (int) (Math.random() * 5) + 1; // Randomize ticket purchase (1-5 tickets)
                boolean success = ticketPool.retrieveTickets(ticketsToBuy, customerName);
                if (!success) {
                    System.out.println(customerName + ": Could not purchase tickets. Pool is empty.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted state
                break; // Exit the loop on interruption
            }
        }
        System.out.println(customerName + ": Stopped purchasing tickets.");
    }
}