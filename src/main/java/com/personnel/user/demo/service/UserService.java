package com.personnel.user.demo.service;

import com.personnel.user.demo.dto.UserRequest;
import com.personnel.user.demo.dto.UserResponse;
import com.personnel.user.demo.dto.UserSignInRequest;
import com.personnel.user.demo.dto.UserSignInResponse;
import com.personnel.user.demo.entity.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
    boolean create(UserRequest user);
    Page<UserResponse> findAll(int page, int size);
    Page<UserResponse> findAllByRole(String role, int page, int size);

    Optional<User> findByEmail(String subject);

    UserSignInResponse signIn(UserSignInRequest user);
}
