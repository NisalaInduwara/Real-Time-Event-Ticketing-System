package com.example.eventticketingsystem.service;

import com.example.eventticketingsystem.model.SystemConfiguration;
import com.example.eventticketingsystem.service.TicketManagementService;
import com.example.eventticketingsystem.model.TicketPool;
import com.example.eventticketingsystem.vendor.TicketVendor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketVendorService {

    private final TicketManagementService ticketManagementService;
    private final List<Thread> vendorThreads = new ArrayList<>();

    public TicketVendorService(TicketManagementService ticketManagementService) {
        this.ticketManagementService = ticketManagementService;
    }

    public void startVendors(SystemConfiguration config) {
        TicketPool ticketPool = ticketManagementService.getTicketPool();

        for (int i = 1; i <= 3; i++) { // Simulate 3 vendors
            TicketVendor vendor = new TicketVendor("Vendor-" + i, ticketPool, config.getTicketReleaseRate());
            Thread vendorThread = new Thread(vendor);
            vendorThread.setName("Vendor-" + i + "-Thread");
            vendorThreads.add(vendorThread);
            vendorThread.start();
        }
    }

    public void stopVendors() {
        for (Thread thread : vendorThreads) {
            thread.interrupt();
        }
        vendorThreads.clear();
    }

}
