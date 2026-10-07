package com.cagri.ticket_reservation_system.repository;

import com.cagri.ticket_reservation_system.entity.Reservation;
import com.cagri.ticket_reservation_system.enums.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findReservationsByUserId(Long userId);

    List<Reservation> findReservationsByStatus(ReservationStatus status);

    List<Reservation> findByExpiresAtBeforeAndStatus(LocalDateTime expiresAt, ReservationStatus reservationStatus);
}
