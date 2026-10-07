package com.cagri.ticket_reservation_system.dto.request;

import com.cagri.ticket_reservation_system.enums.TicketStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketStatusUpdateRequest {

    @NotNull
    private TicketStatus status;
}
