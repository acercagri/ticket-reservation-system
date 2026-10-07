package com.cagri.ticket_reservation_system.service.impl;

import com.cagri.ticket_reservation_system.dto.request.EventSeatCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.EventSeatResponse;
import com.cagri.ticket_reservation_system.entity.Event;
import com.cagri.ticket_reservation_system.entity.EventSeat;
import com.cagri.ticket_reservation_system.entity.Seat;
import com.cagri.ticket_reservation_system.enums.SeatStatus;
import com.cagri.ticket_reservation_system.mapper.EventSeatMapper;
import com.cagri.ticket_reservation_system.repository.EventRepository;
import com.cagri.ticket_reservation_system.repository.EventSeatRepository;
import com.cagri.ticket_reservation_system.repository.SeatRepository;
import com.cagri.ticket_reservation_system.service.EventSeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventSeatServiceImpl implements EventSeatService {

    @Autowired
    private EventSeatRepository eventSeatRepository;

    @Autowired
    private EventSeatMapper eventSeatMapper;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SeatRepository seatRepository;


    @Override
    public EventSeatResponse createEventSeat(EventSeatCreateRequest request) {

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(()->new RuntimeException("event not found"));

        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(()->new RuntimeException("seat not found"));

        EventSeat eventSeat= eventSeatMapper.toEventSeat(request);

        if(!event.getVenue().getId().equals(seat.getVenue().getId())){
            throw new RuntimeException("Seat does not belong to event venue");
        }

        eventSeat.setEvent(event);
        eventSeat.setSeat(seat);

        EventSeat savedEventSeat = eventSeatRepository.save(eventSeat);

        return eventSeatMapper.toEventSeatResponse(savedEventSeat);
    }

    @Override
    public EventSeatResponse findEventSeatById(Long id) {
        EventSeat eventSeat=eventSeatRepository.findById(id)
                .orElseThrow(()->new RuntimeException("event seat not found"));

        return eventSeatMapper.toEventSeatResponse(eventSeat);
    }

    @Override
    public List<EventSeatResponse> findEventSeatsByEvent(Long eventId) {

        Event event=eventRepository.findById(eventId)
                .orElseThrow(()->new RuntimeException("event not found"));

        return eventSeatRepository.findEventSeatByEvent(event)
                .stream()
                .map(eventSeatMapper::toEventSeatResponse)
                .toList();
    }

    @Override
    public List<EventSeatResponse> findEventSeatsByEventAndStatus(Long eventId, SeatStatus status) {

        Event event= eventRepository.findById(eventId)
                .orElseThrow(()->new RuntimeException("event not found"));

        return eventSeatRepository.findEventSeatByEventAndStatus(event,status)
                .stream()
                .map(eventSeatMapper::toEventSeatResponse)
                .toList();
    }

    @Override
    public void deleteEventSeatById(Long id) {
        EventSeat eventSeat=eventSeatRepository.findById(id)
                .orElseThrow(()->new RuntimeException("event seat not found"));
        eventSeatRepository.deleteById(id);
    }
}
