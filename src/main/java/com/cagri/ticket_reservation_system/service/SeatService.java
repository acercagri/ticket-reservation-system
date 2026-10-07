package com.cagri.ticket_reservation_system.service;

import com.cagri.ticket_reservation_system.dto.request.SeatCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.SeatResponse;

import java.util.List;

public interface SeatService {

    SeatResponse createSeat(SeatCreateRequest request);
    SeatResponse findSeatById(Long id);
    List<SeatResponse> findAllSeats();
    List<SeatResponse> findSeatsByVenue(Long venueId);
    void deleteSeatById(Long id);
}
