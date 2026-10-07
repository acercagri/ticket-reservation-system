package com.cagri.ticket_reservation_system.repository;

import com.cagri.ticket_reservation_system.entity.Seat;
import com.cagri.ticket_reservation_system.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllSeatsByVenue(Venue venue);
}
