package com.example.eventticketingsystem.controller;

import com.example.eventticketingsystem.model.SystemConfiguration;
import com.example.eventticketingsystem.model.TicketPool;
import com.example.eventticketingsystem.service.ConfigurationService;
import com.example.eventticketingsystem.service.TicketVendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/configuration")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private TicketVendorService ticketVendorService;

    @GetMapping
    public ResponseEntity<SystemConfiguration> getConfiguration() {
        SystemConfiguration configuration = configurationService.getConfiguration();
        return ResponseEntity.ok(configuration);
    }

    @PostMapping
    public ResponseEntity<String> saveConfiguration(@RequestBody SystemConfiguration configuration) {
        configurationService.saveConfiguration(configuration);
        return ResponseEntity.ok("Configuration saved successfully!");
    }

    @PostMapping("/start-vendors")
    public ResponseEntity<String> startVendors() {
        SystemConfiguration config = configurationService.getConfiguration();
        ticketVendorService.startVendors(config);
        return ResponseEntity.ok("Vendors started successfully!");
    }

    @GetMapping("/ticket-pool")
    public ResponseEntity<TicketPool> getTicketPoolStatus() {
        TicketPool ticketPool = ticketVendorService.getTicketPool();
        return ResponseEntity.ok(ticketPool);
    }
}
