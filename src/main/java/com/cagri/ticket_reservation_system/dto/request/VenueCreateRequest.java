package com.cagri.ticket_reservation_system.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VenueCreateRequest {

    @NotBlank(message = "name cannot be empty.")
    private String name;

    @NotBlank(message = "city cannot be empty.")
    private String city;

    @NotBlank(message = "address cannot be empty.")
    private String address;
}
