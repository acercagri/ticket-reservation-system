package com.cagri.ticket_reservation_system.entity;

import com.cagri.ticket_reservation_system.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "tickets")
public class Ticket extends BaseEntity{

    @JoinColumn(name = "reservation_item_id", nullable = false)
    @OneToOne(fetch = FetchType.LAZY)
    private ReservationItem reservationItem;

    @Column(nullable = false, unique = true)
    private String ticketCode;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}
