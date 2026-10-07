package com.cagri.ticket_reservation_system.entity;

import com.cagri.ticket_reservation_system.enums.EventStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "events")
@NoArgsConstructor
public class Event extends BaseEntity {

    private String name;

    private String description;

    @NotNull
    private LocalDateTime startDateTime;

    @JoinColumn(name = "venue_id",nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Venue venue;

    @NotNull
    @Enumerated(EnumType.STRING)
    private EventStatus status;
}
