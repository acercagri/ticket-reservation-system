import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { VenueResponse, VenueCreateRequest } from '../models/venue.models';

@Injectable({ providedIn: 'root' })
export class VenueService {
  private readonly base = `${environment.apiUrl}/api/venues`;

  constructor(private http: HttpClient) {}

  createVenue(request: VenueCreateRequest): Observable<VenueResponse> {
    return this.http.post<VenueResponse>(this.base, request);
  }

  findAllVenues(): Observable<VenueResponse[]> {
    return this.http.get<VenueResponse[]>(this.base);
  }

  findVenueById(id: number): Observable<VenueResponse> {
    return this.http.get<VenueResponse>(`${this.base}/${id}`);
  }

  findVenueByCity(city: string): Observable<VenueResponse[]> {
    return this.http.get<VenueResponse[]>(`${this.base}/city/${city}`);
  }

  deleteVenue(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
