import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ServiceApiService } from '../services/service-api.service';
import { ServiceModel } from '../models/service.model';

@Component({
  standalone: true,
  selector: 'app-service-list',
  templateUrl: './service-list.component.html',
  imports: [CommonModule],
})
export class ServiceListComponent {
  private serviceApi = inject(ServiceApiService);
  private router = inject(Router);

  services: ServiceModel[] = [];

  ngOnInit() {
    this.loadServices();
  }

  loadServices() {
    this.serviceApi.getAll().subscribe((data) => (this.services = data));
  }

  viewService(id: string) {
    this.router.navigate(['/services', id]);
  }

  editService(id: string) {
    this.router.navigate(['/services', id, 'edit']);
  }

  createService() {
    this.router.navigate(['/services', 'new']);
  }

  deleteService(id: string) {
    this.serviceApi.delete(id).subscribe(() => this.loadServices());
  }
}
