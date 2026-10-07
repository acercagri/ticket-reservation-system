package com.cagri.ticket_reservation_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReservationCreateRequest {



    @NotNull(message = "event id cannot be empty.")
    private Long eventId;

    @NotEmpty(message = "event seat ids cannot be empty.")
    private List<Long> eventSeatIds;
}
