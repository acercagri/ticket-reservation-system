package com.cagri.ticket_reservation_system.service;

import com.cagri.ticket_reservation_system.dto.request.PaymentCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.PaymentResponse;
import com.cagri.ticket_reservation_system.enums.PaymentStatus;

import java.util.List;

public interface PaymentService {

    PaymentResponse processPayment(PaymentCreateRequest request);
    PaymentResponse findPaymentById(Long id);
    List<PaymentResponse> findPaymentsByReservation(Long reservationId);
    List<PaymentResponse> findPaymentsByStatus(PaymentStatus status);
}
