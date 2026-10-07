package com.cagri.ticket_reservation_system.mapper;

import com.cagri.ticket_reservation_system.dto.request.PaymentCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.PaymentResponse;
import com.cagri.ticket_reservation_system.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public Payment toPayment(PaymentCreateRequest request) {
        Payment payment = new Payment();
        return  payment;
    }

    public PaymentResponse toPaymentResponse(Payment payment) {
        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setId(payment.getId());
        paymentResponse.setStatus(payment.getStatus());
        paymentResponse.setAmount(payment.getAmount());
        paymentResponse.setPaymentDate(payment.getPaymentDate());
        paymentResponse.setCreatedAt(payment.getCreatedAt());
        paymentResponse.setUpdatedAt(payment.getUpdatedAt());
        paymentResponse.setReservationId(payment.getReservation().getId());
        return paymentResponse;
    }
}
