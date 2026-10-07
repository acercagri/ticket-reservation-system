package com.cagri.ticket_reservation_system.dto.request;

import com.cagri.ticket_reservation_system.enums.EventStatus;
import com.cagri.ticket_reservation_system.enums.SeatStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EventSeatCreateRequest {

    @NotNull(message = "event id cannot be empty.")
    private Long eventId;

    @NotNull(message = "seat id cannot be empty.")
    private Long seatId;

    @Positive
    @NotNull(message = "price cannot be empty.")
    private BigDecimal price;

    @NotNull(message = "status cannot be empty.")
    private SeatStatus status;
}
