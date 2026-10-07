package com.cagri.ticket_reservation_system.service;

import com.cagri.ticket_reservation_system.dto.request.ReservationCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.ReservationResponse;
import com.cagri.ticket_reservation_system.enums.ReservationStatus;

import java.util.List;

public interface ReservationService {

    ReservationResponse createReservation(ReservationCreateRequest request);
    ReservationResponse findReservationById(Long id);
    List<ReservationResponse> findReservationsByUser(Long userId);
    List<ReservationResponse> findReservationsByStatus(ReservationStatus status);
    ReservationResponse cancelReservation(Long id);
    void expirePendingReservations();
}
