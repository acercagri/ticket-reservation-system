package com.cagri.ticket_reservation_system.service.impl;

import com.cagri.ticket_reservation_system.dto.request.VenueCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.VenueResponse;
import com.cagri.ticket_reservation_system.entity.Venue;
import com.cagri.ticket_reservation_system.mapper.VenueMapper;
import com.cagri.ticket_reservation_system.repository.VenueRepository;
import com.cagri.ticket_reservation_system.service.VenueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VenueServiceImpl implements VenueService {

    @Autowired
    private VenueRepository venueRepository;

    @Autowired
    private VenueMapper  venueMapper;


    @Override
    public VenueResponse createVenue(VenueCreateRequest request) {

        Venue venue = venueMapper.toVenue(request);

        Venue savedVenue = venueRepository.save(venue);

        return venueMapper.toVenueResponse(savedVenue);
    }

    @Override
    public VenueResponse findVenueById(Long venueId) {

        Venue venue=venueRepository.findById(venueId)
                .orElseThrow(()-> new RuntimeException("Venue not found"));

        return venueMapper.toVenueResponse(venue);
    }

    @Override
    public List<VenueResponse> findAllVenue() {
        return venueRepository.findAll()
                .stream()
                .map(venueMapper::toVenueResponse)
                .toList();
    }

    @Override
    public List<VenueResponse> findVenueByCity(String city) {

        return venueRepository.findVenuesByCity(city)
                .stream()
                .map(venueMapper::toVenueResponse)
                .toList();
    }

    @Override
    public void deleteVenueById(Long venueId) {

        Venue venue=venueRepository.findById(venueId)
                .orElseThrow(()-> new RuntimeException("Venue not found"));

        venueRepository.delete(venue);

    }
}
