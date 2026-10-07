package com.cagri.ticket_reservation_system.controller;

import com.cagri.ticket_reservation_system.dto.request.SeatCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.SeatResponse;
import com.cagri.ticket_reservation_system.service.SeatService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SeatResponse createSeat(@Valid @RequestBody SeatCreateRequest request){
        return seatService.createSeat(request);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public SeatResponse findSeatById(@PathVariable Long id){
        return seatService.findSeatById(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public List<SeatResponse> findAllSeats(){
        return seatService.findAllSeats();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/venue/{venueId}")
    public List<SeatResponse> findSeatsByVenue(@PathVariable Long venueId){
        return seatService.findSeatsByVenue(venueId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteSeatById(@PathVariable Long id){
        seatService.deleteSeatById(id);
    }
}
