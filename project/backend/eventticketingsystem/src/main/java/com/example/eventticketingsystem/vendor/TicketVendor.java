package com.example.eventticketingsystem.vendor;

import com.example.eventticketingsystem.model.Ticket;
import com.example.eventticketingsystem.model.TicketPool;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketVendor implements Runnable{

    private final String vendorName;
    private final TicketPool ticketPool;
    private final int ticketsToAdd;
    private static final AtomicInteger ticketIdGenerator = new AtomicInteger(1);

    public TicketVendor(String vendorName, TicketPool ticketPool, int ticketReleaseRate) {
        this.vendorName = vendorName;
        this.ticketPool = ticketPool;
        this.ticketsToAdd = ticketReleaseRate;
    }

    @Override
    public void run() {
        while (true) {
            List<Ticket> tickets = new ArrayList<>();
            for (int i = 0; i < ticketsToAdd; i++) {
                tickets.add(new Ticket(ticketIdGenerator.getAndIncrement()));
            }
            ticketPool.addTickets(tickets);
            try {
                Thread.sleep(900000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}
