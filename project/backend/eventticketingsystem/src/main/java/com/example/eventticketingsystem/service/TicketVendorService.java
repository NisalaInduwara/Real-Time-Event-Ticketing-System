package com.example.eventticketingsystem.service;

import com.example.eventticketingsystem.model.SystemConfiguration;
import com.example.eventticketingsystem.vendor.VendorEntity;
import com.example.eventticketingsystem.model.TicketPool;
import com.example.eventticketingsystem.repository.VendorRepo;
import com.example.eventticketingsystem.vendor.TicketVendor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketVendorService {

    @Autowired
    VendorRepo vendorRepo;
    private final TicketManagementService ticketManagementService;
    private final List<Thread> vendorThreads = new ArrayList<>();

    public int getVendorCount(){
        return vendorRepo.findAll().size();
    }

    public List<String> getVendors(){
        List<String> vendors = new ArrayList<>();
        return vendorRepo.findAll()
                .stream()
                .map(VendorEntity::getVendorName)
                .toList();
    }

    public TicketVendorService(TicketManagementService ticketManagementService) {
        this.ticketManagementService = ticketManagementService;
    }

    public void startVendors(SystemConfiguration config) {
        TicketPool ticketPool = ticketManagementService.getTicketPool();
        int vendors = getVendorCount();
        List<String> vendorNames = getVendors();

        for (int i = 0; i < vendors; i++) {
            String vendorName = vendorNames.get(i);
            TicketVendor vendor = new TicketVendor(vendorName, ticketPool, config.getTicketReleaseRate());
            Thread vendorThread = new Thread(vendor);
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

    public boolean addVendor(String vendorName) {
        try {
            VendorEntity vendor = new VendorEntity();
            vendor.setVendorName(vendorName);
            vendorRepo.save(vendor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteVendor(String vendorName) {
        try {
            VendorEntity vendor = vendorRepo.findByVendorName(vendorName);
            vendorRepo.delete(vendor);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
