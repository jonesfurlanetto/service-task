import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ServiceApiService } from '../services/service-api.service';
import { ServiceModel } from '../models/service.model';

@Component({
  standalone: true,
  selector: 'app-service-form',
  templateUrl: './service-form.component.html',
  imports: [CommonModule, FormsModule],
})
export class ServiceFormComponent implements OnInit {
  private serviceApi = inject(ServiceApiService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  service: ServiceModel = { id: '', resources: [] };
  isEdit = false;

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id && id !== 'new') {
      this.isEdit = true;
      this.serviceApi.getById(id).subscribe((data) => (this.service = data));
    }
  }

  addResource() {
    this.service.resources.push({
      id: '',
      owners: [
        { id: '', name: '', accountNumber: '', level: 0 }
      ]
    });
  }

  addOwner(resourceIndex: number) {
    this.service.resources[resourceIndex].owners.push({
      id: '',
      name: '',
      accountNumber: '',
      level: 0
    });
  }

  save() {
    const obs = this.isEdit
      ? this.serviceApi.update(this.service)
      : this.serviceApi.create(this.service);

    obs.subscribe(() => this.router.navigate(['/services']));
  }
}