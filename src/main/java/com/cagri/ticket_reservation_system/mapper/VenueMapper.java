package com.cagri.ticket_reservation_system.mapper;

import com.cagri.ticket_reservation_system.dto.request.VenueCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.VenueResponse;
import com.cagri.ticket_reservation_system.entity.Venue;
import org.springframework.stereotype.Component;

@Component
public class VenueMapper {

    public Venue toVenue(VenueCreateRequest   venueCreateRequest) {
        Venue venue = new Venue();
        venue.setAddress(venueCreateRequest.getAddress());
        venue.setCity(venueCreateRequest.getCity());
        venue.setName(venueCreateRequest.getName());
        return venue;

    }

    public VenueResponse toVenueResponse(Venue venue) {
        VenueResponse venueResponse = new VenueResponse();
        venueResponse.setId(venue.getId());
        venueResponse.setName(venue.getName());
        venueResponse.setAddress(venue.getAddress());
        venueResponse.setCity(venue.getCity());
        venueResponse.setCreatedAt(venue.getCreatedAt());
        venueResponse.setUpdatedAt(venue.getUpdatedAt());
        return venueResponse;
    }
}
