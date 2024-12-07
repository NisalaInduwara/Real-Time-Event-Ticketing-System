package com.example.eventticketingsystem.controller;

import com.example.eventticketingsystem.model.LogEntry;
import com.example.eventticketingsystem.model.SystemConfiguration;
import com.example.eventticketingsystem.model.TicketPool;
import com.example.eventticketingsystem.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/configuration")
@CrossOrigin(origins = "*")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private TicketVendorService ticketVendorService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TicketManagementService ticketManagementService;

    @Autowired
    private LogService logService;

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
    public ResponseEntity<Map<String, String>> saveConfiguration(@RequestBody SystemConfiguration configuration) {
        Map<String, String> response = new HashMap<>();
        try {
            configurationService.saveConfiguration(configuration);
            response.put("message", "Configuration saved successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "Failed to save configuration.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/start")
    public ResponseEntity<Map<String, String>> startSystem() {
        // Prepare the response map to send as JSON
        Map<String, String> response = new HashMap<>();

        try {
            SystemConfiguration config = configurationService.getConfiguration();
            ticketManagementService.initializeTicketPool(config.getMaxTicketCapacity());

            ticketVendorService.startVendors(config);

            if (config.getCustomerRetrievalRate() > 0) {
                TicketPool ticketPool = ticketManagementService.getTicketPool();
                customerService.startCustomers(ticketPool, config.getCustomerRetrievalRate());
            }

            response.put("message", "System started successfully with ticket pool, vendors, and customers initialized.");
            return ResponseEntity.ok(response); // HTTP 200
        } catch (Exception e) {
            response.put("message", "Failed to start the system: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // HTTP 500
        }
    }


    @PostMapping("/stop")
    public ResponseEntity<Map<String, String>> stopSystem() {
        // Prepare the response map to send as JSON
        Map<String, String> response = new HashMap<>();

        try {
            // Stopping customers, vendors, and clearing ticket pool
            customerService.stopCustomers();
            ticketVendorService.stopVendors();
            ticketManagementService.clearTicketPool();

            response.put("message", "System stopped successfully, including customers, vendors, and ticket pool.");
            return ResponseEntity.ok(response); // HTTP 200
        } catch (Exception e) {
            response.put("message", "Failed to stop the system: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // HTTP 500
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

    @PostMapping("/add-customer")
    public ResponseEntity<Map<String, String>> addCustomer(@RequestBody String customerNameJson) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(customerNameJson);
        String customerName = jsonNode.get("customerName").asText();

        Map<String, String> response = new HashMap<>();
        try {
            boolean added = customerService.addCustomer(customerName);
            if (added) {
                response.put("message", "Customer added successfully!");
                return ResponseEntity.ok(response);  // HTTP 200
            } else {
                response.put("message", "Failed to add customer: " + customerName);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);  // HTTP 400
            }
        } catch (Exception e) {
            response.put("message", "Error adding customer: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);  // HTTP 500
        }
    }

    @DeleteMapping("/delete-customer")
    public ResponseEntity<Map<String, String>> deleteCustomerByName(@RequestParam String customerName) {
        // Prepare the response map to send as JSON
        Map<String, String> response = new HashMap<>();
        try {
            boolean deleted = customerService.deleteCustomer(customerName);
            if (deleted) {
                response.put("message", "Customer deleted successfully: " + customerName);
                return ResponseEntity.ok(response); // HTTP 200
            } else {
                response.put("message", "Customer not found: " + customerName);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // HTTP 404
            }
        } catch (Exception e) {
            response.put("message", "Error deleting customer: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // HTTP 500
        }
    }


    @PostMapping("/add-vendor")
    public ResponseEntity<Map<String, String>> addVendor(@RequestBody String vendorNameJson) throws JsonProcessingException {
        // Convert the incoming JSON string to extract the vendorName
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(vendorNameJson);
        String vendorName = jsonNode.get("vendorName").asText();

        // Prepare the response map to send as JSON
        Map<String, String> response = new HashMap<>();

        try {
            boolean added = ticketVendorService.addVendor(vendorName);
            if (added) {
                response.put("message", "Vendor added successfully: " + vendorName);
                return ResponseEntity.ok(response); // HTTP 200
            } else {
                response.put("message", "Failed to add vendor: " + vendorName);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response); // HTTP 400
            }
        } catch (Exception e) {
            response.put("message", "Error adding vendor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // HTTP 500
        }
    }


    @DeleteMapping("/delete-vendor")
    public ResponseEntity<Map<String, String>> deleteVendorByName(@RequestParam String vendorName) {
        // Prepare the response map to send as JSON
        Map<String, String> response = new HashMap<>();

        try {
            boolean deleted = ticketVendorService.deleteVendor(vendorName);
            if (deleted) {
                response.put("message", "Vendor deleted successfully: " + vendorName);
                return ResponseEntity.ok(response); // HTTP 200
            } else {
                response.put("message", "Vendor not found: " + vendorName);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response); // HTTP 404
            }
        } catch (Exception e) {
            response.put("message", "Error deleting vendor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response); // HTTP 500
        }
    }


    @GetMapping("/customers")
    public List<String> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/vendors")
    public List<String> getVendors() {
        return ticketVendorService.getVendors();
    }

    @GetMapping("/log-entries")
    public List<LogEntry> getLogEntries() {
        return logService.getLogEntries();
    }

}




