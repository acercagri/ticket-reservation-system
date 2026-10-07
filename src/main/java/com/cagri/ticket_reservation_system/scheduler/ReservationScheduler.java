package com.cagri.ticket_reservation_system.scheduler;

import com.cagri.ticket_reservation_system.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReservationScheduler {

    @Autowired
    private ReservationService reservationService;

    @Scheduled(fixedRate = 60000)
    public void expireReservations() {
        reservationService.expirePendingReservations();
    }
}
