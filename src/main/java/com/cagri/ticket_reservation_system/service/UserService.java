package com.cagri.ticket_reservation_system.service;

import com.cagri.ticket_reservation_system.dto.request.UserCreateRequest;
import com.cagri.ticket_reservation_system.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserCreateRequest request);

    UserResponse findUserById(Long id);

    List<UserResponse> findAllUsers();

    void deleteUserById(Long id);


}
