package com.example.eventticketingsystem.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicketPool {

    private final int maxCapacity;
    private final List<Ticket> tickets;
    private static final Logger logger = LoggerFactory.getLogger(TicketPool.class);


    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.tickets = Collections.synchronizedList(new ArrayList<>());
    }

    public synchronized boolean addTickets(List<Ticket> newTickets) {
        try {
            if (tickets.size() + newTickets.size() <= maxCapacity) {
                tickets.addAll(newTickets);
                logger.info("Added {} tickets. Current pool size: {}", newTickets.size(), tickets.size());
                return true;
            } else {
                logger.warn("Cannot add tickets. Pool is at max capacity.");
                return false;
            }
        } catch (Exception e) {
            logger.error("An error occurred while adding tickets: ", e);
            return false;
        }
    }

    public synchronized List<Ticket> removeTicket(int ticketsPerPurchase) {
        try {
            if (tickets.size() >= ticketsPerPurchase) {
                List<Ticket> removedTickets = new ArrayList<>();
                for (int i = 0; i < ticketsPerPurchase; i++) {
                    removedTickets.add(tickets.remove(0));
                }
                logger.info("Removed {} tickets. Current pool size: {}", ticketsPerPurchase, tickets.size());
                return removedTickets;
            } else {
                logger.warn("Not enough tickets available to remove. Requested: {}, Available: {}", ticketsPerPurchase, tickets.size());
                return null;
            }
        } catch (Exception e) {
            logger.error("An error occurred while removing tickets: ", e);
            return null;
        }
    }

    public synchronized int getCurrentSize() {
        try {
            return tickets.size();
        } catch (Exception e) {
            logger.error("An error occurred while getting the current size: ", e);
            return -1;
        }
    }

    public synchronized int getMaxCapacity() {
        try {
            return maxCapacity;
        } catch (Exception e) {
            logger.error("An error occurred while getting the max capacity: ", e);
            return -1;
        }
    }

}
