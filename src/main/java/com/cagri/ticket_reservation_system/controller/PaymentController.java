package com.cagri.ticket_reservation_system.controller;

import com.cagri.ticket_reservation_system.dto.request.PaymentCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.PaymentResponse;
import com.cagri.ticket_reservation_system.enums.PaymentStatus;
import com.cagri.ticket_reservation_system.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse processPayment(@Valid @RequestBody PaymentCreateRequest request){
        return paymentService.processPayment(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public PaymentResponse findPaymentById(@PathVariable Long id){
        return paymentService.findPaymentById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/reservation/{reservationId}")
    public List<PaymentResponse> findPaymentsByReservation(@PathVariable Long reservationId){
        return paymentService.findPaymentsByReservation(reservationId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/status/{status}")
    public List<PaymentResponse> findPaymentsByStatus(@PathVariable PaymentStatus status){
        return paymentService.findPaymentsByStatus(status);
    }

}
