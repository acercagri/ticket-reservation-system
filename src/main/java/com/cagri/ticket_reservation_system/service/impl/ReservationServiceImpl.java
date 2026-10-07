package com.cagri.ticket_reservation_system.service.impl;

import com.cagri.ticket_reservation_system.dto.request.ReservationCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.ReservationResponse;
import com.cagri.ticket_reservation_system.entity.*;
import com.cagri.ticket_reservation_system.enums.ReservationStatus;
import com.cagri.ticket_reservation_system.enums.SeatStatus;
import com.cagri.ticket_reservation_system.mapper.ReservationMapper;
import com.cagri.ticket_reservation_system.repository.*;
import com.cagri.ticket_reservation_system.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationMapper reservationMapper;

    @Autowired
    private ReservationItemRepository reservationItemRepository;

    @Autowired
    private UserRepository  userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventSeatRepository eventSeatRepository;


    @Transactional
    @Override
    public ReservationResponse createReservation(ReservationCreateRequest request) {

        long distinctSeatCount = request.getEventSeatIds()
                .stream()
                .distinct()
                .count();

        if (distinctSeatCount != request.getEventSeatIds().size()) {
            throw new RuntimeException("Duplicate event seats are not allowed");
        }



        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(()->new RuntimeException("Event not found"));

        List<EventSeat> eventSeats=request.getEventSeatIds()
                .stream()
                .map(eventSeatId -> eventSeatRepository.findById(eventSeatId)
                        .orElseThrow(() -> new RuntimeException("Event seat not found")))
                .toList();



        for(EventSeat eventSeat:eventSeats){

            if(!eventSeat.getEvent().getId().equals(event.getId())){
                throw new RuntimeException("Event seat does not belong to event");
            }

            if(eventSeat.getStatus() != SeatStatus.AVAILABLE){
                throw new RuntimeException("Event seat is not available");
            }

        }

        BigDecimal totalAmount = eventSeats.stream()
                .map(EventSeat::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        LocalDateTime now = LocalDateTime.now();

        Reservation reservation = reservationMapper.toReservation(request);

        reservation.setUser(user);
        reservation.setEvent(event);
        reservation.setReservationDate(now);
        reservation.setExpiresAt(now.plusMinutes(10));
        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setTotalAmount(totalAmount);

        Reservation savedReservation = reservationRepository.save(reservation);

        for (EventSeat eventSeat : eventSeats) {

            eventSeat.setStatus(SeatStatus.RESERVED);
            eventSeatRepository.save(eventSeat);

            ReservationItem reservationItem = new ReservationItem();
            reservationItem.setReservation(savedReservation);
            reservationItem.setEventSeat(eventSeat);

            reservationItemRepository.save(reservationItem);
        }

        return reservationMapper.toReservationResponse(savedReservation);

    }

    @Override
    public ReservationResponse findReservationById(Long id) {

        Reservation reservation=reservationRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Reservation not found"));

        checkReservationOwnership(reservation);

        return reservationMapper.toReservationResponse(reservation);
    }

    @Override
    public List<ReservationResponse> findReservationsByUser(Long userId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(authority ->
                        authority.getAuthority().equals("ROLE_ADMIN"));

        Long targetUserId;

        if (isAdmin) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            targetUserId = user.getId();

        } else {
            User authenticatedUser = userRepository
                    .findByUsername(authentication.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            targetUserId = authenticatedUser.getId();
        }

        return reservationRepository.findReservationsByUserId(targetUserId)
                .stream()
                .map(reservationMapper::toReservationResponse)
                .toList();
    }

    @Override
    public List<ReservationResponse> findReservationsByStatus(ReservationStatus status) {
        return reservationRepository.findReservationsByStatus(status)
                .stream()
                .map(reservationMapper::toReservationResponse)
                .toList();
    }

    @Transactional
    @Override
    public ReservationResponse cancelReservation(Long id) {

        Reservation reservation=reservationRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Reservation not found"));

        checkReservationOwnership(reservation);

        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new RuntimeException("Only pending reservations can be cancelled");
        }

        List<ReservationItem> reservationItems=reservationItemRepository.findReservationItemsByReservation(reservation);

        for(ReservationItem item:reservationItems){
            EventSeat eventSeat=item.getEventSeat();
            eventSeat.setStatus(SeatStatus.AVAILABLE);
            eventSeatRepository.save(eventSeat);
        }
        reservation.setStatus(ReservationStatus.CANCELLED);

        Reservation savedReservation = reservationRepository.save(reservation);

        return reservationMapper.toReservationResponse(savedReservation);
    }

    @Transactional
    @Override
    public void expirePendingReservations() {
        List<Reservation> expiredReservations=reservationRepository.findByExpiresAtBeforeAndStatus(LocalDateTime.now(), ReservationStatus.PENDING);

        for(Reservation reservation:expiredReservations){
            List<ReservationItem> reservationItems=reservationItemRepository.findReservationItemsByReservation(reservation);

            for(ReservationItem item:reservationItems){
                EventSeat eventSeat=item.getEventSeat();
                eventSeat.setStatus(SeatStatus.AVAILABLE);
                eventSeatRepository.save(eventSeat);
            }
            reservation.setStatus(ReservationStatus.EXPIRED);
            reservationRepository.save(reservation);
        }
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
                    "You are not authorized to access this reservation."
            );
        }
    }
}
