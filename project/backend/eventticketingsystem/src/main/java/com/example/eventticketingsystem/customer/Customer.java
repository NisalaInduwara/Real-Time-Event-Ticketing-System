package com.example.eventticketingsystem.customer;

import com.example.eventticketingsystem.model.Ticket;
import com.example.eventticketingsystem.model.TicketPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Customer implements Runnable{

    private final String customerName;
    private final TicketPool ticketPool;
    private final int ticketsPerPurchase;
    private static final Logger logger = LoggerFactory.getLogger(Customer.class);

    public Customer(String customerName, TicketPool ticketPool, int ticketsPerPurchase) {
        this.customerName = customerName;
        this.ticketPool = ticketPool;
        this.ticketsPerPurchase = ticketsPerPurchase;
    }

    @Override
    public void run() {
        while (true) {
            List<Ticket> purchasedTickets = ticketPool.removeTicket(ticketsPerPurchase);
            if (purchasedTickets != null && !purchasedTickets.isEmpty()) {
                logger.info("{} purchased tickets: {}", customerName, purchasedTickets);
            } else {
                logger.warn("{} could not purchase tickets. Not enough available.", customerName);
            }
            try {
                Thread.sleep(120000); // Simulate time delay between purchases
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
