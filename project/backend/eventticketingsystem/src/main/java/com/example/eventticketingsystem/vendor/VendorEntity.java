package com.example.eventticketingsystem.vendor;

import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Entity
@Table(name = "vendors")
public class VendorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String vendorName;

    @Transient
    private static final Logger logger = LoggerFactory.getLogger(VendorEntity.class);

    public VendorEntity() {}

    public String getVendorName() {
        return vendorName;
    }

    public void setCustomerName(String customerName) {
        this.vendorName = customerName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }
}
