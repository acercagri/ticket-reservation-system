package com.cagri.ticket_reservation_system.service;

import com.cagri.ticket_reservation_system.dto.request.EventCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.EventResponse;
import com.cagri.ticket_reservation_system.enums.EventStatus;

import java.util.List;

public interface EventService {

    EventResponse createEvent(EventCreateRequest request);
    EventResponse findEventById(Long id);
    List<EventResponse> findAllEvents();
    List<EventResponse> findEventsByVenue(Long venueId);
    List<EventResponse> findEventByStatus(EventStatus status);
    void deleteEventById(Long id);
}
