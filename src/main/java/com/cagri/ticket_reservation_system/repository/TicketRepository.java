package com.cagri.ticket_reservation_system.repository;

import com.cagri.ticket_reservation_system.entity.ReservationItem;
import com.cagri.ticket_reservation_system.entity.Ticket;
import com.cagri.ticket_reservation_system.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findTicketByTicketCode(String ticketCode);

    List<Ticket> findTicketByStatus(TicketStatus status);

    Optional<Ticket> findTicketByReservationItem(ReservationItem reservationItem);
}
