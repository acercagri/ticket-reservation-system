package com.cagri.ticket_reservation_system.service.impl;

import com.cagri.ticket_reservation_system.dto.request.PaymentCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.PaymentResponse;
import com.cagri.ticket_reservation_system.entity.Payment;
import com.cagri.ticket_reservation_system.entity.Reservation;
import com.cagri.ticket_reservation_system.entity.ReservationItem;
import com.cagri.ticket_reservation_system.entity.Ticket;
import com.cagri.ticket_reservation_system.enums.PaymentStatus;
import com.cagri.ticket_reservation_system.enums.ReservationStatus;
import com.cagri.ticket_reservation_system.enums.SeatStatus;
import com.cagri.ticket_reservation_system.enums.TicketStatus;
import com.cagri.ticket_reservation_system.mapper.PaymentMapper;
import com.cagri.ticket_reservation_system.repository.PaymentRepository;
import com.cagri.ticket_reservation_system.repository.ReservationItemRepository;
import com.cagri.ticket_reservation_system.repository.ReservationRepository;
import com.cagri.ticket_reservation_system.repository.TicketRepository;
import com.cagri.ticket_reservation_system.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationItemRepository reservationItemRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Transactional
    @Override
    public PaymentResponse processPayment(PaymentCreateRequest request) {

        Reservation reservation=reservationRepository.findById(request.getReservationId())
                .orElseThrow(()->new RuntimeException("Reservation not found"));

        checkReservationOwnership(reservation);


        if(reservation.getStatus() != ReservationStatus.PENDING){
            throw new RuntimeException("Reservation status is not PENDING");
        }

        if(reservation.getExpiresAt().isBefore(LocalDateTime.now())){
            throw new RuntimeException("Reservation has expired");
        }

        Payment payment=new Payment();
        payment.setReservation(reservation);
        payment.setAmount(reservation.getTotalAmount());
        payment.setPaymentDate(LocalDateTime.now());

        if(Boolean.FALSE.equals(request.getSuccessful())){
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            return paymentMapper.toPaymentResponse(payment);
        }
        payment.setStatus(PaymentStatus.PAID);
        reservation.setStatus(ReservationStatus.CONFIRMED);

        List<ReservationItem> reservationItems=reservationItemRepository.findReservationItemsByReservation(reservation);

        for(ReservationItem item:reservationItems){
            item.getEventSeat().setStatus(SeatStatus.SOLD);

            Ticket ticket = new Ticket();
            ticket.setReservationItem(item);
            ticket.setStatus(TicketStatus.VALID);
            ticket.setTicketCode(UUID.randomUUID().toString());
            ticketRepository.save(ticket);

        }

        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.toPaymentResponse(savedPayment);
    }

    @Override
    public PaymentResponse findPaymentById(Long id) {
        Payment payment=paymentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Payment not found"));

        return paymentMapper.toPaymentResponse(payment);
    }

    @Override
    public List<PaymentResponse> findPaymentsByReservation(Long reservationId) {

        Reservation reservation=reservationRepository.findById(reservationId)
                .orElseThrow(()->new RuntimeException("Reservation not found"));

        return paymentRepository.findPaymentsByReservation(reservation)
                .stream()
                .map(paymentMapper::toPaymentResponse)
                .toList();
    }

    @Override
    public List<PaymentResponse> findPaymentsByStatus(PaymentStatus status) {
        return paymentRepository.findPaymentsByStatus(status)
                .stream()
                .map(paymentMapper::toPaymentResponse)
                .toList();
    }

    private void checkReservationOwnership(Reservation reservation) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return;
        }

        String username = authentication.getName();

        if (!reservation.getUser().getUsername().equals(username)) {
            throw new RuntimeException(
                    "You are not authorized to make payment for this reservation."
            );
        }
    }
}
