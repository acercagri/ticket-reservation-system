package com.cagri.ticket_reservation_system.repository;

import com.cagri.ticket_reservation_system.entity.Reservation;
import com.cagri.ticket_reservation_system.entity.ReservationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationItemRepository extends JpaRepository<ReservationItem, Long> {

    List<ReservationItem> findReservationItemsByReservation(Reservation reservation);
}
