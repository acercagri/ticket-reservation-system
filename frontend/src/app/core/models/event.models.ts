export type EventStatus = 'DRAFT' | 'ACTIVE' | 'CANCELLED' | 'COMPLETED';

export interface EventResponse {
  id: number;
  name: string;
  description: string;
  startDateTime: string;
  venueId: number;
  venueName: string;
  status: EventStatus;
  createdAt: string;
  updatedAt: string;
}

export interface EventCreateRequest {
  name: string;
  description: string;
  startDateTime: string;
  venueId: number;
  status: EventStatus;
}
