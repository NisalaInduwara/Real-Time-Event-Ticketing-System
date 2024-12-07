import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LogsComponent } from './logs/logs.component';
import { ConfigurationsComponent } from './configurations/configurations.component';

export const routes: Routes = [
  {
    path: '',
    redirectTo: 'home',
    pathMatch: 'full',
  },
  {
    path: 'home',
    component: HomeComponent,
  },
  {
    path: 'logs',
    component: LogsComponent,
  },
  {
    path: 'configurations',
    component: ConfigurationsComponent,
  },
];
