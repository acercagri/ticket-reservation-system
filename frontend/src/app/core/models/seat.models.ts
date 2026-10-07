export interface SeatResponse {
  id: number;
  rowName: string;
  seatNumber: number;
  venueId: number;
  venueName: string;
  createdAt: string;
  updatedAt: string;
}

export interface SeatCreateRequest {
  rowName: string;
  seatNumber: number;
  venueId: number;
}
