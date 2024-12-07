package com.example.eventticketingsystem.service;

import com.example.eventticketingsystem.customer.Customer;
import com.example.eventticketingsystem.customer.CustomerEntity;
import com.example.eventticketingsystem.model.TicketPool;
import com.example.eventticketingsystem.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepo customerRepo;

    private final List<Thread> customerThreads = new ArrayList<>();

    public int getCustomerCount() {
        return customerRepo.findAll().size();
    }

    public List<String> getCustomers() {
        List<String> customers = new ArrayList<>();
        return customerRepo.findAll()
                .stream()
                .map(CustomerEntity::getCustomerName)
                .toList();
    }

    public void startCustomers(TicketPool ticketPool, int ticketsPerPurchase) {
        int customerCount = getCustomerCount();
        List<String> customers = getCustomers();
        for (int i = 0; i < customerCount; i++) {
            String customerName = customers.get(i);
            Customer customer = new Customer(customerName, ticketPool, ticketsPerPurchase);
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

    public boolean addCustomer(String customerName) {
        try {
            CustomerEntity customer = new CustomerEntity();
            customer.setCustomerName(customerName);
            customerRepo.save(customer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteCustomer(String customerName) {
        try {
            CustomerEntity customer = customerRepo.findByCustomerName(customerName);
            customerRepo.delete(customer);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
