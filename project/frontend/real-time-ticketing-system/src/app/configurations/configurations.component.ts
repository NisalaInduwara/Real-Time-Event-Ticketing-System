import { HttpClient } from '@angular/common/http';
import { Component, inject, OnInit } from '@angular/core';
import { inject as angularInject } from '@angular/core';
import { IConfiguration } from '../model/interface/configuration';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-configurations',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './configurations.component.html',
  styleUrl: './configurations.component.css',
})
export class ConfigurationsComponent implements OnInit {
  http = inject(HttpClient);
  formBuilder = inject(FormBuilder);

  configurationForm: FormGroup;
  data: IConfiguration = {} as IConfiguration;

  constructor() {
    this.configurationForm = this.formBuilder.group({
      totalTickets: [0, [Validators.required, Validators.min(1)]],
      ticketReleaseRate: [0, [Validators.required, Validators.min(1)]],
      customerRetrievalRate: [0, [Validators.required, Validators.min(1)]],
      maxTicketCapacity: [0, [Validators.required, Validators.min(1)]],
    });
  }

  ngOnInit(): void {
    this.getConfigurations();
  }

  getConfigurations() {
    this.http
      .get<IConfiguration>('http://localhost:8080/api/configuration')
      .subscribe((res) => {
        this.data = res;
        this.configurationForm.setValue({
          totalTickets: res.totalTickets,
          ticketReleaseRate: res.ticketReleaseRate,
          customerRetrievalRate: res.customerRetrievalRate,
          maxTicketCapacity: res.maxTicketCapacity,
        });
      });
  }

  saveConfigurations() {
    if (this.configurationForm.valid) {
      this.http
        .post<{ message: string }>(
          'http://localhost:8080/api/configuration',
          this.configurationForm.value
        )
        .subscribe({
          next: (response) => {
            console.log('Server response:', response.message);
            alert(response.message);
          },
          error: (err) => {
            console.error('Error saving configuration:', err);
            alert(err.error?.message || 'Failed to save configuration.');
          },
        });
    } else {
      alert('Please correct the form errors before saving.');
    }
  }
}
