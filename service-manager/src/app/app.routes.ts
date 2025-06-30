import { Routes } from '@angular/router';
import { ServiceListComponent } from './features/services/pages/service-list.component';
import { ServiceDetailComponent } from './features/services/pages/service-detail.component';
import { ServiceFormComponent } from './features/services/pages/service-form.component';

export const routes: Routes = [
  { path: '', redirectTo: 'services', pathMatch: 'full' },
  { path: 'services', component: ServiceListComponent },
  { path: 'services/new', component: ServiceFormComponent },
  { path: 'services/:id', component: ServiceDetailComponent },
  { path: 'services/:id/edit', component: ServiceFormComponent },
];