package com.cagri.ticket_reservation_system.dto.response;

import com.cagri.ticket_reservation_system.enums.TicketStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TicketResponse {

    private Long id;

    private String ticketCode;
    private TicketStatus status;
    private Long reservationItemId;
    private Long eventId;
    private String eventName;
    private Long seatId;
    private String rowName;
    private Integer seatNumber;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
