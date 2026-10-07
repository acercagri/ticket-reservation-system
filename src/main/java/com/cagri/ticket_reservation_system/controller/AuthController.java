package com.cagri.ticket_reservation_system.controller;

import com.cagri.ticket_reservation_system.dto.request.LoginRequest;
import com.cagri.ticket_reservation_system.dto.request.RegisterRequest;
import com.cagri.ticket_reservation_system.dto.response.AuthResponse;
import com.cagri.ticket_reservation_system.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    AuthResponse register(@Valid @RequestBody RegisterRequest request){
        return authService.register(request);
    }

    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    AuthResponse login(@Valid @RequestBody LoginRequest request){
        return authService.login(request);
    }
}
