package com.cagri.ticket_reservation_system.repository;

import com.cagri.ticket_reservation_system.entity.Event;
import com.cagri.ticket_reservation_system.entity.EventSeat;
import com.cagri.ticket_reservation_system.enums.SeatStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventSeatRepository extends JpaRepository<EventSeat, Long> {

   List<EventSeat> findEventSeatByEvent(Event event);

   List<EventSeat> findEventSeatByEventAndStatus(Event event, SeatStatus status);
}
