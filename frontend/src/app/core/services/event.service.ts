import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { EventResponse, EventCreateRequest, EventStatus } from '../models/event.models';

@Injectable({ providedIn: 'root' })
export class EventService {
  private readonly base = `${environment.apiUrl}/api/events`;

  constructor(private http: HttpClient) {}

  createEvent(request: EventCreateRequest): Observable<EventResponse> {
    return this.http.post<EventResponse>(this.base, request);
  }

  findAllEvents(): Observable<EventResponse[]> {
    return this.http.get<EventResponse[]>(this.base);
  }

  findEventById(id: number): Observable<EventResponse> {
    return this.http.get<EventResponse>(`${this.base}/${id}`);
  }

  findEventsByVenue(venueId: number): Observable<EventResponse[]> {
    return this.http.get<EventResponse[]>(`${this.base}/venue/${venueId}`);
  }

  findEventsByStatus(status: EventStatus): Observable<EventResponse[]> {
    return this.http.get<EventResponse[]>(`${this.base}/status/${status}`);
  }

  deleteEvent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
