package com.cagri.ticket_reservation_system.entity;

import com.cagri.ticket_reservation_system.enums.SeatStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "event_seats",
uniqueConstraints = {
        @UniqueConstraint(name = "event_seat",
        columnNames = {"event_id","seat_id"})
})
public class EventSeat extends BaseEntity {

    @JoinColumn(name = "event_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Event event;

    @JoinColumn(name = "seat_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Seat seat;

    @Positive
    @NotNull
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private SeatStatus status;

    @Version
    private Long version;
}
