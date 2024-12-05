package com.example.eventticketingsystem.service;

import org.springframework.stereotype.Service;
import com.example.eventticketingsystem.model.SystemConfiguration;
import com.example.eventticketingsystem.utils.ConfigurationUtil;

@Service
public class ConfigurationService {


    private static final String CONFIG_FILE_PATH = "src/main/resources/configurations/system-config.json";

    public SystemConfiguration getConfiguration() {
        try {
            return ConfigurationUtil.loadConfiguration(CONFIG_FILE_PATH, SystemConfiguration.class);
        } catch (Exception e) {
            return null;
        }
    }

    public void saveConfiguration(SystemConfiguration configuration) {
        ConfigurationUtil.saveConfiguration(CONFIG_FILE_PATH, configuration);
    }

}
