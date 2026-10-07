package com.cagri.ticket_reservation_system.service;

import com.cagri.ticket_reservation_system.dto.request.TicketStatusUpdateRequest;
import com.cagri.ticket_reservation_system.dto.response.TicketResponse;
import com.cagri.ticket_reservation_system.enums.TicketStatus;

import java.util.List;

public interface TicketService {

    TicketResponse findTicketById(Long id);
    TicketResponse findTicketByTicketCode(String ticketCode);
    List<TicketResponse> findTicketsByStatus(TicketStatus status);
    TicketResponse findTicketByReservationItem(Long reservationItemId);
    TicketResponse updateTicketStatus(Long id, TicketStatusUpdateRequest request);
}
