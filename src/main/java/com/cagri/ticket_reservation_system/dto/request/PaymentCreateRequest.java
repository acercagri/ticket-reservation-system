package com.cagri.ticket_reservation_system.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentCreateRequest {

    @NotNull(message = "reservation id cannot be empty.")
    private Long reservationId;

    @NotNull
    private Boolean successful;
}
