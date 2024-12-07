import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, interval, switchMap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class HomeService {
  private ticketPoolUrl = 'http://localhost:8080/api/configuration/ticket-pool';
  private apiUrl = 'http://localhost:8080/api/configuration';

  constructor(private http: HttpClient) {}

  getTicketPoolSize(): Observable<{
    maxCapacity: number;
    currentSize: number;
  }> {
    return this.http.get<{ maxCapacity: number; currentSize: number }>(
      this.ticketPoolUrl
    );
  }

  getTicketPoolUpdates(): Observable<number> {
    return interval(5000).pipe(
      switchMap(() => this.getTicketPoolSize()),
      switchMap((data) => [data.currentSize])
    );
  }

  getCustomers(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/customers`);
  }

  getVendors(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/vendors`);
  }

  addCustomer(customerName: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/add-customer`, { customerName });
  }

  deleteCustomer(customerName: string): Observable<any> {
    return this.http.delete<any>(
      `${this.apiUrl}/delete-customer?customerName=${customerName}`
    );
  }

  addVendor(vendorName: string): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/add-vendor`, { vendorName });
  }

  deleteVendor(vendorName: string): Observable<any> {
    return this.http.delete<any>(
      `${this.apiUrl}/delete-vendor?vendorName=${vendorName}`
    );
  }
}
