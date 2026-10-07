package com.cagri.ticket_reservation_system.controller;

import com.cagri.ticket_reservation_system.dto.request.EventCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.EventResponse;
import com.cagri.ticket_reservation_system.enums.EventStatus;
import com.cagri.ticket_reservation_system.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventResponse createEvent(@Valid  @RequestBody EventCreateRequest request){
        return eventService.createEvent(request);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public EventResponse findEventById(@PathVariable Long id){

        return eventService.findEventById(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public List<EventResponse> findAllEvents(){

        return eventService.findAllEvents();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/venue/{venueId}")
    public List<EventResponse> findEventsByVenue(@PathVariable Long venueId){
        return eventService.findEventsByVenue(venueId);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/status/{status}")
    public List<EventResponse> findEventByStatus(@PathVariable EventStatus status){
        return eventService.findEventByStatus(status);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteEventById(@PathVariable Long id){
        eventService.deleteEventById(id);
    }
}
