package com.example.eventticketingsystem.service;

import com.example.eventticketingsystem.customer.Customer;
import com.example.eventticketingsystem.model.TicketPool;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    private final List<Thread> customerThreads = new ArrayList<>();

    public void startCustomers(TicketPool ticketPool, int customerCount, int ticketsPerPurchase) {
        for (int i = 1; i <= customerCount; i++) {
            Customer customer = new Customer("Customer-" + i, ticketPool, ticketsPerPurchase);
            Thread customerThread = new Thread(customer);
            customerThreads.add(customerThread);
            customerThread.start();
        }
    }

    public void stopCustomers() {
        for (Thread thread : customerThreads) {
            thread.interrupt();
        }
    }
}
