package com.example.eventticketingsystem.repository;

import com.example.eventticketingsystem.vendor.VendorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepo extends JpaRepository<VendorEntity, Long> {
    VendorEntity findByVendorName(String vendorName);
}
