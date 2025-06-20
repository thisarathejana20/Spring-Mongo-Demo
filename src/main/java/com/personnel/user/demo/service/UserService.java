package com.personnel.user.demo.service;

import com.personnel.user.demo.dto.UserRequest;
import com.personnel.user.demo.dto.UserResponse;
import org.springframework.data.domain.Page;

public interface UserService {
    boolean create(UserRequest user);
    Page<UserResponse> findAll(int page, int size);
    Page<UserResponse> findAllByRole(String role, int page, int size);
}
