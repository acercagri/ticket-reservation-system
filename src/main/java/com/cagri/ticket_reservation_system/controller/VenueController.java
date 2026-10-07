package com.cagri.ticket_reservation_system.controller;


import com.cagri.ticket_reservation_system.dto.request.VenueCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.VenueResponse;
import com.cagri.ticket_reservation_system.service.VenueService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    @Autowired
    private VenueService venueService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VenueResponse createVenue(@Valid @RequestBody VenueCreateRequest request){
        return venueService.createVenue(request);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{venueId}")
    public VenueResponse findVenueById(@PathVariable Long venueId){
        return venueService.findVenueById(venueId);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public List<VenueResponse> findAllVenue(){
        return venueService.findAllVenue();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/city/{city}")
    public List<VenueResponse> findVenueByCity(@PathVariable String city){
        return venueService.findVenueByCity(city);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{venueId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteVenueById(@PathVariable Long venueId){
        venueService.deleteVenueById(venueId);
    }
}
