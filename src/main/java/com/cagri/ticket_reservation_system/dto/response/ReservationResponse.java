package com.cagri.ticket_reservation_system.dto.response;

import com.cagri.ticket_reservation_system.enums.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationResponse {

    private Long id;

    private Long userId;
    private String userName;
    private Long eventId;
    private String eventName;
    private LocalDateTime reservationDate;
    private LocalDateTime expiresAt;
    private ReservationStatus status;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
