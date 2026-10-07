export type SeatStatus = 'AVAILABLE' | 'RESERVED' | 'SOLD';

export interface EventSeatResponse {
  id: number;
  eventId: number;
  eventName: string;
  seatId: number;
  rowName: string;
  seatNumber: number;
  price: number;
  status: SeatStatus;
  version: number;
  createdAt: string;
  updatedAt: string;
}

export interface EventSeatCreateRequest {
  eventId: number;
  seatId: number;
  price: number;
  status: SeatStatus;
}
