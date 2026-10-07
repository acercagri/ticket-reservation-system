package com.cagri.ticket_reservation_system.service;

import com.cagri.ticket_reservation_system.dto.request.EventSeatCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.EventSeatResponse;
import com.cagri.ticket_reservation_system.enums.SeatStatus;

import java.util.List;

public interface EventSeatService {

    EventSeatResponse createEventSeat(EventSeatCreateRequest request);
    EventSeatResponse findEventSeatById(Long id);
    List<EventSeatResponse> findEventSeatsByEvent(Long eventId);
    List<EventSeatResponse> findEventSeatsByEventAndStatus(Long eventId, SeatStatus status);
    void deleteEventSeatById(Long id);


}
