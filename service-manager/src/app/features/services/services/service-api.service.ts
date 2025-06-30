import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ServiceModel } from '../models/service.model';

@Injectable({ providedIn: 'root' })
export class ServiceApiService {
  private baseUrl = 'http://localhost:8080/api/services';

  constructor(private http: HttpClient) {}

  getAll(): Observable<ServiceModel[]> {
    return this.http.get<ServiceModel[]>(`${this.baseUrl}/all`);
  }

  getById(id: string): Observable<ServiceModel> {
    return this.http.get<ServiceModel>(`${this.baseUrl}/fetch?serviceId=${id}`);
  }

  create(service: ServiceModel): Observable<any> {
    return this.http.post(`${this.baseUrl}/create`, service);
  }

  update(service: ServiceModel): Observable<any> {
    return this.http.put(`${this.baseUrl}/update`, service);
  }

  delete(id: string): Observable<any> {
    return this.http.delete(`${this.baseUrl}/delete?serviceId=${id}`);
  }
}