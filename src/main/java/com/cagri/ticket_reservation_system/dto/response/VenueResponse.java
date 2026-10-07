package com.cagri.ticket_reservation_system.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class VenueResponse {

    private Long id;
    private String name;
    private String city;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
