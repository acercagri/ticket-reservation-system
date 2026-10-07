package com.cagri.ticket_reservation_system.dto.response;

import com.cagri.ticket_reservation_system.enums.EventStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventResponse {


    private Long id;

    private String name;
    private String description;
    private LocalDateTime startDateTime;
    private Long venueId;
    private String venueName;
    private EventStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
