package com.cagri.ticket_reservation_system.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SeatResponse {

    private Long id;

    private String rowName;
    private Integer seatNumber;
    private Long venueId;
    private String venueName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
