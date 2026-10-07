package com.cagri.ticket_reservation_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name = "seats")
@Entity
@NoArgsConstructor
public class Seat extends BaseEntity {

    private String rowName;

    @Positive
    private Integer seatNumber;


    @JoinColumn(name = "venue_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Venue venue;

}
