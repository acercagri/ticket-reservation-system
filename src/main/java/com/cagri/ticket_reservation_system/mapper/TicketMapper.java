package com.cagri.ticket_reservation_system.mapper;

import com.cagri.ticket_reservation_system.dto.request.TicketStatusUpdateRequest;
import com.cagri.ticket_reservation_system.dto.response.TicketResponse;
import com.cagri.ticket_reservation_system.entity.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {


    public TicketResponse toTicketResponse(Ticket ticket) {

        TicketResponse ticketResponse = new TicketResponse();

        ticketResponse.setId(ticket.getId());
        ticketResponse.setTicketCode(ticket.getTicketCode());
        ticketResponse.setStatus(ticket.getStatus());

        ticketResponse.setReservationItemId(
                ticket.getReservationItem().getId()
        );


        ticketResponse.setEventId(
                ticket.getReservationItem().getEventSeat().getEvent().getId()
        );

        ticketResponse.setEventName(
                ticket.getReservationItem().getEventSeat().getEvent().getName()
        );

        ticketResponse.setSeatId(
                ticket.getReservationItem().getEventSeat().getSeat().getId()
        );

        ticketResponse.setRowName(
                ticket.getReservationItem().getEventSeat().getSeat().getRowName()
        );

        ticketResponse.setSeatNumber(
                ticket.getReservationItem().getEventSeat().getSeat().getSeatNumber()
        );

        ticketResponse.setCreatedAt(ticket.getCreatedAt());
        ticketResponse.setUpdatedAt(ticket.getUpdatedAt());

        return ticketResponse;
    }
}
