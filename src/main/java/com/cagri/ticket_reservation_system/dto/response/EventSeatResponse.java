package com.cagri.ticket_reservation_system.dto.response;

import com.cagri.ticket_reservation_system.enums.EventStatus;
import com.cagri.ticket_reservation_system.enums.SeatStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class EventSeatResponse {

    private Long id;

    private Long eventId;
    private String eventName;
    private Long seatId;
    private  String rowName;
    private Integer seatNumber;
    private BigDecimal price;
    private SeatStatus status;
    private Long version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
