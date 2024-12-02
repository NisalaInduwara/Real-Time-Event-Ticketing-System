package com.example.eventticketingsystem.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketPool {

    private int currentTickets;
    private final int maxCapacity;
    private final Lock lock = new ReentrantLock();

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.currentTickets = 0;
    }

    public boolean addTickets(int ticketsToAdd) {
        lock.lock();
        try {
            if (currentTickets + ticketsToAdd <= maxCapacity) {
                currentTickets += ticketsToAdd;
                System.out.println("Added " + ticketsToAdd + " tickets. Current pool: " + currentTickets);
                return true;
            } else {
                System.out.println("Cannot add " + ticketsToAdd + " tickets. Pool is at max capacity.");
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    public int getCurrentTickets() {
        return currentTickets;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

}
