package com.ticketing.model;
/**
 * Represents a record of an action in the com.com.ticketing system.
 */
public class TicketRecord {
    private final String actionType; // "ADD" or "RETRIEVE"
    private final String entityName; // Name of the vendor or customer
    private final int ticketCount; // Number of tickets involved in the action
    private final int remainingTickets; // Tickets remaining in the pool after the action

    /**
     * Initializes a TicketRecord object.
     *
     * @param actionType       Type of action ("ADD" or "RETRIEVE").
     * @param entityName       Name of the entity performing the action.
     * @param ticketCount      Number of tickets involved.
     * @param remainingTickets Tickets remaining after the action.
     */
    public TicketRecord(String actionType, String entityName, int ticketCount, int remainingTickets) {
        this.actionType = actionType;
        this.entityName = entityName;
        this.ticketCount = ticketCount;
        this.remainingTickets = remainingTickets;
    }

    // Getters for ticket record details
    public String getActionType() {
        return actionType;
    }

    public String getEntityName() {
        return entityName;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public int getRemainingTickets() {
        return remainingTickets;
    }

    @Override
    public String toString() {
        return "TicketRecord[actionType=" + actionType +
                ", entityName=" + entityName +
                ", ticketCount=" + ticketCount +
                ", remainingTickets=" + remainingTickets + "]";
    }
}
