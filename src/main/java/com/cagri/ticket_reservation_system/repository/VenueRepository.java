package com.cagri.ticket_reservation_system.repository;

import com.cagri.ticket_reservation_system.entity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueRepository extends JpaRepository<Venue, Long> {

    List<Venue> findVenuesByCity(String city);
}
