package com.ticketing.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manages ticket additions and purchases in a synchronized, thread-safe manner.
 */
public class TicketPool {
    private int totalTickets;
    private final int maxCapacity;
    private final List<TicketRecord> transactions = Collections.synchronizedList(new ArrayList<>());

    /**
     * Initializes the TicketPool with initial tickets and maximum capacity.
     *
     * @param initialTickets  Initial number of tickets in the pool.
     * @param maxCapacity     Maximum ticket capacity for the pool.
     */
    public TicketPool(int initialTickets, int maxCapacity) {
        this.totalTickets = initialTickets;
        this.maxCapacity = maxCapacity;
    }

    /**
     * Adds tickets to the pool. Blocks if adding tickets would exceed max capacity.
     *
     * @param count      Number of tickets to add.
     * @param vendorName Name of the vendor adding tickets.
     */
    public synchronized void addTickets(int count, String vendorName) {
        while (totalTickets + count > maxCapacity) {
            try {
                System.out.println(vendorName + " waiting to add tickets. Pool is at max capacity.");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        totalTickets += count;
        System.out.println(vendorName + " added " + count + " tickets. Current Pool: " + totalTickets);
        transactions.add(new TicketRecord("ADD", vendorName, count, totalTickets));
        notifyAll();
    }

    /**
     * Purchases tickets from the pool if available.
     *
     * @param count         Number of tickets to purchase.
     * @param customerName  Name of the customer attempting to purchase.
     * @return true if tickets were purchased, false otherwise.
     */
    public synchronized boolean retrieveTickets(int count, String customerName) {
        if (totalTickets >= count) {
            totalTickets -= count;
            System.out.println(customerName + " purchased " + count + " tickets. Current Pool: " + totalTickets);
            transactions.add(new TicketRecord("RETRIEVE", customerName, count, totalTickets));
            notifyAll();
            return true;
        } else {
            System.out.println(customerName + " failed to purchase " + count + " tickets. Not enough tickets.");
            notifyAll();
            return false;
        }
    }

    /**
     * Retrieves the current total number of tickets.
     *
     * @return Total tickets available in the pool.
     */
    public synchronized int getTotalTickets() {
        return totalTickets;
    }

    /**
     * Retrieves a copy of all transactions for safe external use.
     *
     * @return List of transactions.
     */
    public synchronized List<TicketRecord> getTransactions() {
        return new ArrayList<>(transactions);
    }
}