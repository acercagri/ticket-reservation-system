export type PaymentStatus = 'PENDING' | 'PAID' | 'FAILED' | 'REFUNDED';

export interface PaymentResponse {
  id: number;
  reservationId: number;
  amount: number;
  paymentDate: string;
  status: PaymentStatus;
  createdAt: string;
  updatedAt: string;
}

export interface PaymentCreateRequest {
  reservationId: number;
  successful: boolean;
}
