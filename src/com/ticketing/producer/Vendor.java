package com.ticketing.producer;

import com.ticketing.model.TicketPool;

/**
 * Represents a vendor thread that periodically adds tickets to the shared ticket pool.
 */
public class Vendor implements Runnable {
    private final TicketPool ticketPool;
    private final int releaseInterval;
    private final String vendorName;
    private boolean isRunning = true; // Controls the thread's lifecycle

    /**
     * Initializes a com.ticketing.config.producer.Vendor object.
     *
     * @param ticketPool     The shared TicketPool object.
     * @param releaseInterval Interval (milliseconds) between ticket additions.
     * @param vendorName     Name of the vendor.
     */
    public Vendor(TicketPool ticketPool, int releaseInterval, String vendorName) {
        this.ticketPool = ticketPool;
        this.releaseInterval = releaseInterval;
        this.vendorName = vendorName;
    }

    /**
     * Stops the vendor thread.
     */
    public void stopVendor() {
        isRunning = false;
    }

    /**
     * Main execution method for the vendor thread.
     * Periodically adds tickets to the pool until stopped.
     */
    @Override
    public void run() {
        while (isRunning) {
            try {
                Thread.sleep(releaseInterval); // Wait before adding tickets
                int ticketsToAdd = (int) (Math.random() * 10) + 1; // Randomize number of tickets (1-10)
                ticketPool.addTickets(ticketsToAdd, vendorName); // Add tickets to the pool
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                break; // Exit if interrupted
            }
        }
        System.out.println(vendorName + ": Stopped adding tickets.");
    }
}

