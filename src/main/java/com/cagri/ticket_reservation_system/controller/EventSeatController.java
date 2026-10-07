package com.cagri.ticket_reservation_system.controller;

import com.cagri.ticket_reservation_system.dto.request.EventSeatCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.EventSeatResponse;
import com.cagri.ticket_reservation_system.enums.SeatStatus;
import com.cagri.ticket_reservation_system.service.EventSeatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event-seats")
public class EventSeatController {

    @Autowired
    private EventSeatService eventSeatService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventSeatResponse createEventSeat(@Valid @RequestBody EventSeatCreateRequest request){
        return eventSeatService.createEventSeat(request);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public EventSeatResponse findEventSeatById(@PathVariable Long id){
        return eventSeatService.findEventSeatById(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/event/{eventId}")
    public List<EventSeatResponse> findEventSeatsByEvent(@PathVariable Long eventId){
        return eventSeatService.findEventSeatsByEvent(eventId);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/event/{eventId}/status/{status}")
    public List<EventSeatResponse> findEventSeatsByEventAndStatus(@PathVariable Long eventId,@PathVariable SeatStatus status){
        return eventSeatService.findEventSeatsByEventAndStatus(eventId, status);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteEventSeatById(@PathVariable Long id){
        eventSeatService.deleteEventSeatById(id);
    }
}
