import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { TicketResponse, TicketStatus, TicketStatusUpdateRequest } from '../models/ticket.models';

@Injectable({ providedIn: 'root' })
export class TicketService {
  private readonly base = `${environment.apiUrl}/api/tickets`;

  constructor(private http: HttpClient) {}

  findTicketById(id: number): Observable<TicketResponse> {
    return this.http.get<TicketResponse>(`${this.base}/${id}`);
  }

  findTicketByCode(ticketCode: string): Observable<TicketResponse> {
    return this.http.get<TicketResponse>(`${this.base}/ticket-code/${ticketCode}`);
  }

  findTicketsByStatus(status: TicketStatus): Observable<TicketResponse[]> {
    return this.http.get<TicketResponse[]>(`${this.base}/status/${status}`);
  }

  findTicketByReservationItem(reservationItemId: number): Observable<TicketResponse> {
    return this.http.get<TicketResponse>(`${this.base}/reservation-item/${reservationItemId}`);
  }

  updateTicketStatus(id: number, request: TicketStatusUpdateRequest): Observable<TicketResponse> {
    return this.http.patch<TicketResponse>(`${this.base}/${id}/status`, request);
  }
}
