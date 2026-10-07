package com.cagri.ticket_reservation_system.dto.request;

import com.cagri.ticket_reservation_system.enums.EventStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EventCreateRequest {

    @NotBlank(message = "name cannot be empty.")
    private String name;

    @NotBlank(message = "description cannot be empty.")
    private String description;

    @NotNull(message = "start date time cannot be empty.")
    private LocalDateTime startDateTime;

    @NotNull(message = "venue id cannot be empty.")
    private Long venueId;

    @NotNull(message = "status cannot be empty.")
    private EventStatus status;
}
