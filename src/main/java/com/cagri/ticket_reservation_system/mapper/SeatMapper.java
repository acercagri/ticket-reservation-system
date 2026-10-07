package com.cagri.ticket_reservation_system.mapper;

import com.cagri.ticket_reservation_system.dto.request.SeatCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.SeatResponse;
import com.cagri.ticket_reservation_system.entity.Seat;
import org.springframework.stereotype.Component;

@Component
public class SeatMapper {

    public Seat toSeat(SeatCreateRequest seatCreateRequest) {
        Seat seat = new Seat();
        seat.setSeatNumber(seatCreateRequest.getSeatNumber());
        seat.setRowName(seatCreateRequest.getRowName());
        return seat;
    }

    public SeatResponse toSeatResponse(Seat seat) {
        SeatResponse seatResponse = new SeatResponse();
        seatResponse.setId(seat.getId());
        seatResponse.setRowName(seat.getRowName());
        seatResponse.setSeatNumber(seat.getSeatNumber());
        seatResponse.setVenueId(seat.getVenue().getId());
        seatResponse.setVenueName(seat.getVenue().getName());
        seatResponse.setCreatedAt(seat.getCreatedAt());
        seatResponse.setUpdatedAt(seat.getUpdatedAt());
        return seatResponse;
    }
}
