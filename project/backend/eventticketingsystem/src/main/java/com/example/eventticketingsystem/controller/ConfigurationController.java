package com.example.eventticketingsystem.controller;

import com.example.eventticketingsystem.model.SystemConfiguration;
import com.example.eventticketingsystem.model.TicketPool;
import com.example.eventticketingsystem.service.ConfigurationService;
import com.example.eventticketingsystem.service.CustomerService;
import com.example.eventticketingsystem.service.TicketVendorService;
import com.example.eventticketingsystem.service.TicketManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/configuration")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private TicketVendorService ticketVendorService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TicketManagementService ticketManagementService;

    @GetMapping
    public ResponseEntity<SystemConfiguration> getConfiguration() {
        try {
            SystemConfiguration configuration = configurationService.getConfiguration();
            return ResponseEntity.ok(configuration);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping
    public ResponseEntity<String> saveConfiguration(@RequestBody SystemConfiguration configuration) {
        try {
            configurationService.saveConfiguration(configuration);
            return ResponseEntity.ok("Configuration saved successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save configuration.");
        }
    }

    @PostMapping("/start")
    public ResponseEntity<String> startSystem(
            @RequestParam int customerCount,
            @RequestParam int ticketsPerPurchase) {
        try {
            SystemConfiguration config = configurationService.getConfiguration();
            ticketManagementService.initializeTicketPool(config.getMaxTicketCapacity());

            ticketVendorService.startVendors(config);

            if (customerCount > 0 && ticketsPerPurchase > 0) {
                TicketPool ticketPool = ticketManagementService.getTicketPool();
                customerService.startCustomers(ticketPool, customerCount, ticketsPerPurchase);
            }

            return ResponseEntity.ok("System started successfully with ticket pool, vendors, and customers initialized.");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to start the system: " + e.getMessage());
        }
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopSystem() {
        try {
            customerService.stopCustomers();

            ticketVendorService.stopVendors();

            ticketManagementService.clearTicketPool();

            return ResponseEntity.ok("System stopped successfully, including customers, vendors, and ticket pool.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to stop the system." + e.getMessage());
        }
    }

    @GetMapping("/ticket-pool")
    public ResponseEntity<TicketPool> getTicketPoolStatus() {
        try {
            return ResponseEntity.ok(ticketManagementService.getTicketPool());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}





//    @PostMapping("/initialize-ticket-pool")
//    public ResponseEntity<String> initializeTicketPool() {
//        try {
//            SystemConfiguration config = configurationService.getConfiguration();
//            ticketManagementService.initializeTicketPool(config.getMaxTicketCapacity());
//
//            return ResponseEntity.ok("Ticket pool initialized with max capacity: " + config.getMaxTicketCapacity());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to initialize ticket pool.");
//        }
//    }
//
//    @PostMapping("/start-vendors")
//    public ResponseEntity<String> startVendors() {
//        try{
//            SystemConfiguration config = configurationService.getConfiguration();
//            ticketVendorService.startVendors(config);
//            return ResponseEntity.ok("Vendors started successfully!");
//        }catch(Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to start vendors.");
//        }
//
//    }
//
//    @PostMapping("/start-customers")
//    public ResponseEntity<String> startCustomers(@RequestParam int customerCount, @RequestParam int ticketsPerPurchase) {
//        try{
//            TicketPool ticketPool = ticketManagementService.getTicketPool();
//            customerService.startCustomers(ticketPool, customerCount, ticketsPerPurchase);
//            return ResponseEntity.ok("Customers started successfully!");
//        }catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to start customers.");
//        }
//    }
