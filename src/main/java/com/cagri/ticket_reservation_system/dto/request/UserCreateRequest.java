package com.cagri.ticket_reservation_system.dto.request;

import com.cagri.ticket_reservation_system.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {

    @NotBlank(message = "username cannot be empty.")
    private String username;

    @NotBlank(message = "password cannot be empty.")
    private String password;

    @Email
    @NotBlank(message = "email cannot be empty.")
    private String email;

    @NotNull(message = "role cannot be empty.")
    private Role role;
}
