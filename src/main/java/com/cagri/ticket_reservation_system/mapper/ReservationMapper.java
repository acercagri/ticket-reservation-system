package com.cagri.ticket_reservation_system.mapper;

import com.cagri.ticket_reservation_system.dto.request.ReservationCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.ReservationResponse;
import com.cagri.ticket_reservation_system.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {

    public Reservation toReservation(ReservationCreateRequest request) {
        Reservation reservation = new Reservation();
        return   reservation;
    }

    public ReservationResponse toReservationResponse(Reservation reservation) {
        ReservationResponse reservationResponse = new ReservationResponse();
        reservationResponse.setId(reservation.getId());
        reservationResponse.setReservationDate(reservation.getReservationDate());
        reservationResponse.setEventId(reservation.getEvent().getId());
        reservationResponse.setEventName(reservation.getEvent().getName());
        reservationResponse.setUserId(reservation.getUser().getId());
        reservationResponse.setUserName(reservation.getUser().getUsername());
        reservationResponse.setStatus(reservation.getStatus());
        reservationResponse.setExpiresAt(reservation.getExpiresAt());
        reservationResponse.setTotalAmount(reservation.getTotalAmount());
        reservationResponse.setCreatedAt(reservation.getCreatedAt());
        reservationResponse.setUpdatedAt(reservation.getUpdatedAt());
        return reservationResponse;

    }
}
