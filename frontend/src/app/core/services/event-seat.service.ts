import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { EventSeatResponse, EventSeatCreateRequest, SeatStatus } from '../models/event-seat.models';

@Injectable({ providedIn: 'root' })
export class EventSeatService {
  private readonly base = `${environment.apiUrl}/api/event-seats`;

  constructor(private http: HttpClient) {}

  createEventSeat(request: EventSeatCreateRequest): Observable<EventSeatResponse> {
    return this.http.post<EventSeatResponse>(this.base, request);
  }

  findEventSeatById(id: number): Observable<EventSeatResponse> {
    return this.http.get<EventSeatResponse>(`${this.base}/${id}`);
  }

  findEventSeatsByEvent(eventId: number): Observable<EventSeatResponse[]> {
    return this.http.get<EventSeatResponse[]>(`${this.base}/event/${eventId}`);
  }

  findEventSeatsByEventAndStatus(eventId: number, status: SeatStatus): Observable<EventSeatResponse[]> {
    return this.http.get<EventSeatResponse[]>(`${this.base}/event/${eventId}/status/${status}`);
  }

  deleteEventSeat(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
