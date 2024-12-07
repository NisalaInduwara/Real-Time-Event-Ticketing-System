package com.example.eventticketingsystem.repository;

import com.example.eventticketingsystem.customer.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<CustomerEntity, Long> {

    CustomerEntity findByCustomerName(String customerName);
}
