package com.cagri.ticket_reservation_system.entity;

import com.cagri.ticket_reservation_system.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Table(name = "users")
@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User extends BaseEntity{

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
}
