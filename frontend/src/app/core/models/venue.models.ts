export interface VenueResponse {
  id: number;
  name: string;
  city: string;
  address: string;
  createdAt: string;
  updatedAt: string;
}

export interface VenueCreateRequest {
  name: string;
  city: string;
  address: string;
}
