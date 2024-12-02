package com.example.eventticketingsystem.controller;

import com.example.eventticketingsystem.model.SystemConfiguration;
import com.example.eventticketingsystem.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/configuration")
public class ConfigurationController {

    @Autowired
    private ConfigurationService configurationService;

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
}
