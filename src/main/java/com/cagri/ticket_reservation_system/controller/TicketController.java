package com.cagri.ticket_reservation_system.controller;

import com.cagri.ticket_reservation_system.dto.request.TicketStatusUpdateRequest;
import com.cagri.ticket_reservation_system.dto.response.TicketResponse;
import com.cagri.ticket_reservation_system.enums.TicketStatus;
import com.cagri.ticket_reservation_system.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    TicketResponse findTicketById(@PathVariable Long id){
        return ticketService.findTicketById(id);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/ticket-code/{ticketCode}")
    TicketResponse findTicketByTicketCode(@PathVariable String ticketCode){
        return ticketService.findTicketByTicketCode(ticketCode);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/status/{status}")
    List<TicketResponse> findTicketsByStatus(@PathVariable TicketStatus status){
        return ticketService.findTicketsByStatus(status);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/reservation-item/{reservationItemId}")
    TicketResponse findTicketByReservationItem(@PathVariable Long reservationItemId){
        return ticketService.findTicketByReservationItem(reservationItemId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/status")
    TicketResponse updateTicketStatus(
            @PathVariable Long id,
            @Valid @RequestBody TicketStatusUpdateRequest request) {

        return ticketService.updateTicketStatus(id, request);
    }
}
