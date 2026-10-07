package com.cagri.ticket_reservation_system.service.impl;

import com.cagri.ticket_reservation_system.dto.request.SeatCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.SeatResponse;
import com.cagri.ticket_reservation_system.entity.Seat;
import com.cagri.ticket_reservation_system.entity.Venue;
import com.cagri.ticket_reservation_system.mapper.SeatMapper;
import com.cagri.ticket_reservation_system.repository.SeatRepository;
import com.cagri.ticket_reservation_system.repository.VenueRepository;
import com.cagri.ticket_reservation_system.service.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private SeatMapper seatMapper;

    @Autowired
    private VenueRepository venueRepository;

    @Override
    public SeatResponse createSeat(SeatCreateRequest request) {

        Venue venue = venueRepository.findById(request.getVenueId())
                .orElseThrow(()->new RuntimeException("Venue not found"));

        Seat seat = seatMapper.toSeat(request);
        seat.setVenue(venue);

        Seat savedSeat = seatRepository.save(seat);

        return seatMapper.toSeatResponse(savedSeat);
    }

    @Override
    public SeatResponse findSeatById(Long id) {

        Seat seat=seatRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Seat not found"));

        return seatMapper.toSeatResponse(seat);
    }

    @Override
    public List<SeatResponse> findAllSeats() {

        return seatRepository.findAll()
                .stream()
                .map(seatMapper::toSeatResponse)
                .toList();
    }

    @Override
    public List<SeatResponse> findSeatsByVenue(Long venueId) {

        Venue venue=venueRepository.findById(venueId)
                .orElseThrow(()->new RuntimeException("Venue not found"));

        return seatRepository.findAllSeatsByVenue(venue)
                .stream()
                .map(seatMapper::toSeatResponse)
                .toList();
    }

    @Override
    public void deleteSeatById(Long id) {

        Seat seat=seatRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Seat not found"));
        seatRepository.delete(seat);

    }
}
