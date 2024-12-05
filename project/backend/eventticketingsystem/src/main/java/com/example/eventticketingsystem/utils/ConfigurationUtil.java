package com.example.eventticketingsystem.utils;

import com.example.eventticketingsystem.controller.ConfigurationController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;


public class ConfigurationUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationUtil.class);

    public static <T> T loadConfiguration(String filePath, Class<T> clazz) {
        try {
            logger.info("Loading configuration from file: {}", filePath);
            return objectMapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            logger.error("Failed to load configuration from file: {}", filePath, e);
            throw new RuntimeException("Failed to load configuration from file: " + filePath, e);
        }
    }

    public static void saveConfiguration(String filePath, Object configuration) {
        try {
            logger.info("Saving configuration to file: {}", filePath);
            objectMapper.writeValue(new File(filePath), configuration);
        } catch (IOException e) {
            logger.error("Failed to save configuration to file: {}", filePath, e);
            throw new RuntimeException("Failed to save configuration to file: " + filePath, e);
        }
    }
}
