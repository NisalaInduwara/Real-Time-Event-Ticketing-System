import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, inject } from '@angular/core';

@Component({
  selector: 'app-logs',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './logs.component.html',
  styleUrl: './logs.component.css',
})
export class LogsComponent {
  http = inject(HttpClient);
  logs: Array<{ date: string; time: string; description: string }> = [];

  ngOnInit(): void {
    this.getLogs();
  }

  getLogs() {
    this.http
      .get('http://localhost:8080/api/configuration/log-entries')
      .subscribe((res: any) => {
        this.logs = res;
      });
  }
}
