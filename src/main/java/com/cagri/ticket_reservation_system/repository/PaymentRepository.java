package com.cagri.ticket_reservation_system.repository;

import com.cagri.ticket_reservation_system.entity.Payment;
import com.cagri.ticket_reservation_system.entity.Reservation;
import com.cagri.ticket_reservation_system.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findPaymentsByReservation(Reservation reservation);
    List<Payment> findPaymentsByStatus(PaymentStatus paymentStatus);
}
