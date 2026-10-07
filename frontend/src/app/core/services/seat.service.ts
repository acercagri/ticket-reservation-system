import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { SeatResponse, SeatCreateRequest } from '../models/seat.models';

@Injectable({ providedIn: 'root' })
export class SeatService {
  private readonly base = `${environment.apiUrl}/api/seats`;

  constructor(private http: HttpClient) {}

  createSeat(request: SeatCreateRequest): Observable<SeatResponse> {
    return this.http.post<SeatResponse>(this.base, request);
  }

  findAllSeats(): Observable<SeatResponse[]> {
    return this.http.get<SeatResponse[]>(this.base);
  }

  findSeatById(id: number): Observable<SeatResponse> {
    return this.http.get<SeatResponse>(`${this.base}/${id}`);
  }

  findSeatsByVenue(venueId: number): Observable<SeatResponse[]> {
    return this.http.get<SeatResponse[]>(`${this.base}/venue/${venueId}`);
  }

  deleteSeat(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
