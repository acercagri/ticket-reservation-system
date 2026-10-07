export type ReservationStatus = 'PENDING' | 'CONFIRMED' | 'EXPIRED' | 'CANCELLED';

export interface ReservationResponse {
  id: number;
  userId: number;
  userName: string;
  eventId: number;
  eventName: string;
  reservationDate: string;
  expiresAt: string;
  status: ReservationStatus;
  totalAmount: number;
  createdAt: string;
  updatedAt: string;
}

export interface ReservationCreateRequest {
  eventId: number;
  eventSeatIds: number[];
}
