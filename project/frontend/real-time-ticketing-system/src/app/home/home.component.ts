import { Component, Inject, OnInit } from '@angular/core';
import { HomeService } from './home.service';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent implements OnInit {
  constructor(
    @Inject(HomeService) private homeService: HomeService,
    private http: HttpClient
  ) {}

  currentTicketSize: number = 0;
  customers: string[] = [];
  systemRunning: boolean = false;
  vendors: string[] = [];
  newCustomerName: string = '';
  newVendorName: string = '';

  ngOnInit(): void {
    this.fetchTicketPoolSize();
    this.listenForUpdates();
    this.fetchCustomers();
    this.fetchVendors();
  }

  fetchTicketPoolSize(): void {
    this.homeService.getTicketPoolSize().subscribe({
      next: (data) => {
        this.currentTicketSize = data.currentSize;
      },
      error: (err) => {
        console.error('Error fetching ticket pool size:', err);
      },
    });
  }

  listenForUpdates(): void {
    this.homeService.getTicketPoolUpdates().subscribe({
      next: (updatedSize) => {
        this.currentTicketSize = updatedSize;
      },
      error: (err) => {
        console.error('Error receiving ticket pool updates:', err);
      },
    });
  }

  fetchCustomers(): void {
    // Fetch customer list from the backend
    this.homeService.getCustomers().subscribe({
      next: (customers) => {
        this.customers = customers;
      },
      error: (err) => {
        console.error('Error fetching customers:', err);
      },
    });
  }

  fetchVendors(): void {
    // Fetch vendor list from the backend
    this.homeService.getVendors().subscribe({
      next: (vendors) => {
        this.vendors = vendors;
      },
      error: (err) => {
        console.error('Error fetching vendors:', err);
      },
    });
  }

  toggleSystem(): void {
    if (this.systemRunning) {
      // Stop the system
      this.http
        .post('http://localhost:8080/api/configuration/stop', {})
        .subscribe({
          next: () => {
            this.systemRunning = false; // Update the button text
          },
          error: (err: any) => {
            console.error('Error stopping system:', err);
          },
        });
    } else {
      // Start the system
      const ticketsPerPurchase = 5; // Example value, you can replace with dynamic input
      this.http
        .post('http://localhost:8080/api/configuration/start', {
          ticketsPerPurchase,
        })
        .subscribe({
          next: () => {
            this.systemRunning = true; // Update the button text
          },
          error: (err: any) => {
            console.error('Error starting system:', err);
          },
        });
    }
  }

  addCustomer(customerName: string): void {
    if (customerName) {
      this.homeService.addCustomer(customerName).subscribe({
        next: () => {
          this.customers.push(customerName);
          this.newCustomerName = '';
        },
        error: (err) => {
          console.error('Error adding customer:', err);
        },
      });
    }
  }

  deleteCustomer(customerName: string): void {
    this.homeService.deleteCustomer(customerName).subscribe({
      next: () => {
        this.customers = this.customers.filter(
          (customer) => customer !== customerName
        );
      },
      error: (err) => {
        console.error('Error deleting customer:', err);
      },
    });
  }

  addVendor(vendorName: string): void {
    if (vendorName) {
      this.homeService.addVendor(vendorName).subscribe({
        next: () => {
          this.vendors.push(vendorName);
          this.newVendorName = '';
        },
        error: (err) => {
          console.error('Error adding vendor:', err);
        },
      });
    }
  }

  deleteVendor(vendorName: string): void {
    this.homeService.deleteVendor(vendorName).subscribe({
      next: () => {
        this.vendors = this.vendors.filter((vendor) => vendor !== vendorName);
      },
      error: (err) => {
        console.error('Error deleting vendor:', err);
      },
    });
  }
}
