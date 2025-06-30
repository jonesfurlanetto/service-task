import { Component, inject } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ServiceApiService } from '../services/service-api.service';
import { ServiceModel } from '../models/service.model';

@Component({
  standalone: true,
  selector: 'app-service-detail',
  templateUrl: './service-detail.component.html',
  imports: [CommonModule],
})
export class ServiceDetailComponent {
  private serviceApi = inject(ServiceApiService);
  private route = inject(ActivatedRoute);

  service?: ServiceModel;

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id') || '';
    this.serviceApi.getById(id).subscribe((data) => (this.service = data));
  }
}
