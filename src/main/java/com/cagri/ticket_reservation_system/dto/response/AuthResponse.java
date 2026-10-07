package com.cagri.ticket_reservation_system.dto.response;

import com.cagri.ticket_reservation_system.enums.Role;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String token;
    private String username;
    private Role role;
}
