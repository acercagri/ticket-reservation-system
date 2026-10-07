package com.cagri.ticket_reservation_system.mapper;

import com.cagri.ticket_reservation_system.dto.request.EventCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.EventResponse;
import com.cagri.ticket_reservation_system.entity.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public Event toEvent(EventCreateRequest request) {
        Event event = new Event();
        event.setName(request.getName());
        event.setDescription(request.getDescription());
        event.setStartDateTime(request.getStartDateTime());
        event.setStatus(request.getStatus());
        return event;
    }

    public EventResponse toEventResponse(Event event) {
        EventResponse eventResponse = new EventResponse();
        eventResponse.setId(event.getId());
        eventResponse.setName(event.getName());
        eventResponse.setDescription(event.getDescription());
        eventResponse.setStartDateTime(event.getStartDateTime());
        eventResponse.setVenueId(event.getVenue().getId());
        eventResponse.setVenueName(event.getVenue().getName());
        eventResponse.setStatus(event.getStatus());
        eventResponse.setCreatedAt(event.getCreatedAt());
        eventResponse.setUpdatedAt(event.getUpdatedAt());
        return eventResponse;
    }
}
