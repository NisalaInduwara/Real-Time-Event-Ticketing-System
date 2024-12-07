package com.example.eventticketingsystem.service;

import com.example.eventticketingsystem.model.LogEntry;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogService {

    private static final String LOG_FILE_PATH = "src/main/resources/logs/eventticketingsystem.log";

    public List<LogEntry> getLogEntries() {
        List<LogEntry> logEntries = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(LOG_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ", 4);
                if (parts.length == 4) {
                    String date = parts[0];
                    String time = parts[1];
                    String description = parts[3];
                    logEntries.add(new LogEntry(date, time, description));
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to read log file: " + e.getMessage());
        }
        return logEntries;
    }

}
