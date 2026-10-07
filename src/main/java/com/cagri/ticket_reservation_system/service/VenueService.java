package com.cagri.ticket_reservation_system.service;

import com.cagri.ticket_reservation_system.dto.request.VenueCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.VenueResponse;

import java.util.List;

public interface VenueService {

    VenueResponse createVenue(VenueCreateRequest request);

    VenueResponse findVenueById(Long venueId);

    List<VenueResponse> findAllVenue();

    List<VenueResponse> findVenueByCity(String city);

    void deleteVenueById(Long venueId);
}
