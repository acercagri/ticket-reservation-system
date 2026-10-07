import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ReservationResponse, ReservationCreateRequest, ReservationStatus } from '../models/reservation.models';

@Injectable({ providedIn: 'root' })
export class ReservationService {
  private readonly base = `${environment.apiUrl}/api/reservations`;

  constructor(private http: HttpClient) {}

  createReservation(request: ReservationCreateRequest): Observable<ReservationResponse> {
    return this.http.post<ReservationResponse>(this.base, request);
  }

  findReservationById(id: number): Observable<ReservationResponse> {
    return this.http.get<ReservationResponse>(`${this.base}/${id}`);
  }

  findReservationsByUser(userId: number): Observable<ReservationResponse[]> {
    return this.http.get<ReservationResponse[]>(`${this.base}/user/${userId}`);
  }

  findReservationsByStatus(status: ReservationStatus): Observable<ReservationResponse[]> {
    return this.http.get<ReservationResponse[]>(`${this.base}/status/${status}`);
  }

  cancelReservation(id: number): Observable<ReservationResponse> {
    return this.http.patch<ReservationResponse>(`${this.base}/${id}/cancel`, {});
  }
}
