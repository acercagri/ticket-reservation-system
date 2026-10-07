import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { PaymentResponse, PaymentCreateRequest, PaymentStatus } from '../models/payment.models';

@Injectable({ providedIn: 'root' })
export class PaymentService {
  private readonly base = `${environment.apiUrl}/api/payments`;

  constructor(private http: HttpClient) {}

  processPayment(request: PaymentCreateRequest): Observable<PaymentResponse> {
    return this.http.post<PaymentResponse>(this.base, request);
  }

  findPaymentById(id: number): Observable<PaymentResponse> {
    return this.http.get<PaymentResponse>(`${this.base}/${id}`);
  }

  findPaymentsByReservation(reservationId: number): Observable<PaymentResponse[]> {
    return this.http.get<PaymentResponse[]>(`${this.base}/reservation/${reservationId}`);
  }

  findPaymentsByStatus(status: PaymentStatus): Observable<PaymentResponse[]> {
    return this.http.get<PaymentResponse[]>(`${this.base}/status/${status}`);
  }
}
