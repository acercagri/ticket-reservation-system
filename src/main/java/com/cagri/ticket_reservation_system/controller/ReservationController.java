package com.cagri.ticket_reservation_system.controller;

import com.cagri.ticket_reservation_system.dto.request.ReservationCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.ReservationResponse;
import com.cagri.ticket_reservation_system.enums.ReservationStatus;
import com.cagri.ticket_reservation_system.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationResponse createReservation(@Valid  @RequestBody ReservationCreateRequest request){
        return reservationService.createReservation(request);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ReservationResponse findReservationById(@PathVariable Long id){
        return reservationService.findReservationById(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/user/{userId}")
    public List<ReservationResponse> findReservationsByUser(@PathVariable Long userId){
        return reservationService.findReservationsByUser(userId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/status/{status}")
    public List<ReservationResponse> findReservationsByStatus(@PathVariable ReservationStatus status){
        return reservationService.findReservationsByStatus(status);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PatchMapping("/{id}/cancel")
    public ReservationResponse cancelReservation(@PathVariable Long id){
        return reservationService.cancelReservation(id);
    }
}
