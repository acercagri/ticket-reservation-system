package com.cagri.ticket_reservation_system.service.impl;

import com.cagri.ticket_reservation_system.dto.request.EventCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.EventResponse;
import com.cagri.ticket_reservation_system.entity.Event;
import com.cagri.ticket_reservation_system.entity.Venue;
import com.cagri.ticket_reservation_system.enums.EventStatus;
import com.cagri.ticket_reservation_system.mapper.EventMapper;
import com.cagri.ticket_reservation_system.repository.EventRepository;
import com.cagri.ticket_reservation_system.repository.VenueRepository;
import com.cagri.ticket_reservation_system.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventMapper eventMapper;

    @Autowired
    private VenueRepository venueRepository;


    @Override
    public EventResponse createEvent(EventCreateRequest request) {
        Venue venue = venueRepository.findById(request.getVenueId())
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        Event event = eventMapper.toEvent(request);

        event.setVenue(venue);

        Event savedEvent = eventRepository.save(event);

        return eventMapper.toEventResponse(savedEvent);
    }

    @Override
    public EventResponse findEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        return eventMapper.toEventResponse(event);
    }

    @Override
    public List<EventResponse> findAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(eventMapper::toEventResponse)
                .toList();
    }

    @Override
    public List<EventResponse> findEventsByVenue(Long venueId) {

        Venue venue=venueRepository.findById(venueId)
                .orElseThrow(() -> new RuntimeException("Venue not found"));

        return eventRepository.findEventByVenue(venue)
                .stream()
                .map(eventMapper::toEventResponse)
                .toList();
    }

    @Override
    public List<EventResponse> findEventByStatus(EventStatus status) {

        return eventRepository.findEventByStatus(status)
                .stream()
                .map(eventMapper::toEventResponse)
                .toList();
    }

    @Override
    public void deleteEventById(Long id) {

        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        eventRepository.delete(event);

    }
}
