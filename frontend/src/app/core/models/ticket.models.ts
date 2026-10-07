export type TicketStatus = 'VALID' | 'CANCELLED';

export interface TicketResponse {
  id: number;
  ticketCode: string;
  status: TicketStatus;
  reservationItemId: number;
  reservationId: number;
  eventId: number;
  eventName: string;
  seatId: number;
  rowName: string;
  seatNumber: number;
  createdAt: string;
  updatedAt: string;
}

export interface TicketStatusUpdateRequest {
  status: TicketStatus;
}
