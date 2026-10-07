package com.cagri.ticket_reservation_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "reservation_items")
@Entity
@NoArgsConstructor
public class ReservationItem extends BaseEntity {

    @JoinColumn(name = "reservation_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Reservation reservation;

    @JoinColumn(name = "event_seat_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private EventSeat eventSeat;
}
