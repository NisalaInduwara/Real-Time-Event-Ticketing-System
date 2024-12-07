package com.example.eventticketingsystem.service;

import com.example.eventticketingsystem.model.TicketPool;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TicketManagementService {

    private TicketPool ticketPool;
    private static final Logger logger = LoggerFactory.getLogger(TicketManagementService.class);

    public synchronized void initializeTicketPool(int ticketCount) {
        try {
            ticketPool = new TicketPool(ticketCount);
            logger.info("Ticket pool initialized with max capacity: {}", ticketCount);
        } catch (Exception e) {
            logger.error("Error initializing ticket pool: {}", e.getMessage());
        }
    }

    public synchronized TicketPool getTicketPool() {
        if (ticketPool == null) {
            return new TicketPool(0);
        }
        return ticketPool;
    }

    public synchronized void clearTicketPool() {
        try {
            if (ticketPool == null) {
                logger.warn("Ticket pool is already null. No action needed.");
            } else {
                ticketPool = null;
                logger.info("Ticket pool cleared successfully.");
            }
        } catch (Exception e) {
            logger.error("Error clearing ticket pool: {}", e.getMessage());
        }
    }

}
