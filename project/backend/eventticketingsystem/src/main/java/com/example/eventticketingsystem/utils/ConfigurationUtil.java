package com.example.eventticketingsystem.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;

public class ConfigurationUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T loadConfiguration(String filePath, Class<T> clazz) {
        try {
            return objectMapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration from file: " + filePath, e);
        }
    }

    public static void saveConfiguration(String filePath, Object configuration) {
        try {
            objectMapper.writeValue(new File(filePath), configuration);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save configuration to file: " + filePath, e);
        }
    }
}
