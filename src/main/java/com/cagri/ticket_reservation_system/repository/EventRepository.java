package com.cagri.ticket_reservation_system.repository;

import com.cagri.ticket_reservation_system.entity.Event;
import com.cagri.ticket_reservation_system.entity.Venue;
import com.cagri.ticket_reservation_system.enums.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findEventByStatus(EventStatus status);

    List<Event> findEventByVenue(Venue venue);
}
