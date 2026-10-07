package com.cagri.ticket_reservation_system.service.impl;

import com.cagri.ticket_reservation_system.dto.request.TicketStatusUpdateRequest;
import com.cagri.ticket_reservation_system.dto.response.TicketResponse;
import com.cagri.ticket_reservation_system.entity.Reservation;
import com.cagri.ticket_reservation_system.entity.ReservationItem;
import com.cagri.ticket_reservation_system.entity.Ticket;
import com.cagri.ticket_reservation_system.enums.TicketStatus;
import com.cagri.ticket_reservation_system.mapper.TicketMapper;
import com.cagri.ticket_reservation_system.repository.ReservationItemRepository;
import com.cagri.ticket_reservation_system.repository.TicketRepository;
import com.cagri.ticket_reservation_system.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ReservationItemRepository reservationItemRepository;

    @Autowired
    private TicketMapper ticketMapper;


    @Override
    public TicketResponse findTicketById(Long id) {

        Ticket ticket=ticketRepository.findById(id)
                .orElseThrow(()->new RuntimeException("ticket not found"));

        checkTicketOwnership(ticket);


        return ticketMapper.toTicketResponse(ticket);
    }

    @Override
    public TicketResponse findTicketByTicketCode(String ticketCode) {

        Ticket ticket=ticketRepository.findTicketByTicketCode(ticketCode)
                .orElseThrow(()->new RuntimeException("ticket not found"));

        checkTicketOwnership(ticket);

        return ticketMapper.toTicketResponse(ticket);
    }

    @Override
    public List<TicketResponse> findTicketsByStatus(TicketStatus status) {

        return ticketRepository.findTicketByStatus(status)
                .stream()
                .map(ticketMapper::toTicketResponse)
                .toList();
    }

    @Override
    public TicketResponse findTicketByReservationItem(Long reservationItemId) {

        ReservationItem reservationItem=reservationItemRepository.findById(reservationItemId)
                .orElseThrow(()->new RuntimeException("reservationItem not found"));

        Ticket ticket=ticketRepository.findTicketByReservationItem(reservationItem)
                .orElseThrow(()->new RuntimeException("ticket not found"));

        checkTicketOwnership(ticket);

        return  ticketMapper.toTicketResponse(ticket) ;
    }

    @Override
    public TicketResponse updateTicketStatus(Long id, TicketStatusUpdateRequest request) {

        Ticket ticket=ticketRepository.findById(id)
                .orElseThrow(()->new RuntimeException("ticket not found"));

        if(ticket.getStatus().equals(TicketStatus.CANCELLED)
                && request.getStatus().equals(TicketStatus.VALID)) {

            throw new RuntimeException("Cancelled ticket cannot be reactivated");

        }

        ticket.setStatus(request.getStatus());
        Ticket savedTicket = ticketRepository.save(ticket);

        return ticketMapper.toTicketResponse(savedTicket);
    }

    private void checkTicketOwnership(Ticket ticket) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return;
        }

        String username = authentication.getName();

        String ticketOwnerUsername = ticket.getReservationItem()
                .getReservation()
                .getUser()
                .getUsername();

        if (!ticketOwnerUsername.equals(username)) {
            throw new RuntimeException(
                    "You are not authorized to access this ticket."
            );
        }
    }
}
