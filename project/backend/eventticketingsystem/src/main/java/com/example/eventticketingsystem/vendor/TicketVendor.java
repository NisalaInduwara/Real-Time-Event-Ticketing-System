package com.example.eventticketingsystem.vendor;

import com.example.eventticketingsystem.model.TicketPool;

public class TicketVendor implements Runnable{

    private final String vendorName;
    private final TicketPool ticketPool;
    private final int ticketReleaseRate;

    public TicketVendor(String vendorName, TicketPool ticketPool, int ticketReleaseRate) {
        this.vendorName = vendorName;
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
    }

    @Override
    public void run() {
        while (true) {
            boolean added = ticketPool.addTickets(ticketReleaseRate);
            if(added) {
                System.out.println(vendorName + " added " + ticketReleaseRate + " tickets to the pool");
            }
            try{
                Thread.sleep(900000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

}
