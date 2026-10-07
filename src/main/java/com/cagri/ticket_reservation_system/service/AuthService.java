package com.cagri.ticket_reservation_system.service;

import com.cagri.ticket_reservation_system.dto.request.LoginRequest;
import com.cagri.ticket_reservation_system.dto.request.RegisterRequest;
import com.cagri.ticket_reservation_system.dto.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
