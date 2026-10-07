package com.cagri.ticket_reservation_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SeatCreateRequest {

    @NotBlank(message = "row name cannot be empty.")
    private String rowName;

    @Positive
    @NotNull(message = "seat number cannot be empty.")
    private Integer seatNumber;

    @NotNull(message = "venue id cannot be empty.")
    private Long venueId;
}
