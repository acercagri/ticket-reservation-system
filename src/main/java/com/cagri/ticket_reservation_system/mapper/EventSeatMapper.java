package com.cagri.ticket_reservation_system.mapper;

import com.cagri.ticket_reservation_system.dto.request.EventSeatCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.EventSeatResponse;
import com.cagri.ticket_reservation_system.entity.EventSeat;
import org.springframework.stereotype.Component;

@Component
public class EventSeatMapper {

    public EventSeat toEventSeat(EventSeatCreateRequest request) {
        EventSeat eventSeat = new EventSeat();
        eventSeat.setPrice(request.getPrice());
        eventSeat.setStatus(request.getStatus());
        return eventSeat;
    }

    public EventSeatResponse  toEventSeatResponse(EventSeat eventSeat) {
        EventSeatResponse eventSeatResponse = new EventSeatResponse();
        eventSeatResponse.setId(eventSeat.getId());
        eventSeatResponse.setEventId(eventSeat.getEvent().getId());
        eventSeatResponse.setEventName(eventSeat.getEvent().getName());
        eventSeatResponse.setSeatId(eventSeat.getSeat().getId());
        eventSeatResponse.setSeatNumber(eventSeat.getSeat().getSeatNumber());
        eventSeatResponse.setRowName(eventSeat.getSeat().getRowName());
        eventSeatResponse.setPrice(eventSeat.getPrice());
        eventSeatResponse.setStatus(eventSeat.getStatus());
        eventSeatResponse.setVersion(eventSeat.getVersion());
        eventSeatResponse.setCreatedAt(eventSeat.getCreatedAt());
        eventSeatResponse.setUpdatedAt(eventSeat.getUpdatedAt());
        return eventSeatResponse;
    }
}
