package com.personnel.user.demo.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.personnel.user.demo.dto.UserRequest;
import com.personnel.user.demo.dto.UserResponse;
import com.personnel.user.demo.entity.User;
import com.personnel.user.demo.repository.UserCustomRepository;
import com.personnel.user.demo.repository.UserRepopository;
import com.personnel.user.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final ObjectMapper objectMapper;
    private final UserRepopository userRepopository;
    private final PasswordEncoder passwordEncoder;
    private final UserCustomRepository userCustomRepository;

    @Override
    public boolean create(UserRequest user) {
        com.personnel.user.demo.entity.User userDao =
                objectMapper.convertValue(user, com.personnel.user.demo.entity.User.class);
        userDao.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepopository.save(userDao);
        return true;
    }

    @Override
    public Page<UserResponse> findAll(int page, int size) {
        Page<User> userEntityList = userRepopository.findAll(PageRequest.of(page, size));
        return userEntityList.map(user -> objectMapper.convertValue(user, UserResponse.class));
    }

    @Override
    public Page<UserResponse> findAllByRole(String role, int page, int size) {
        Page<User> userEntityList = userCustomRepository.findByRole(role, PageRequest.of(page, size));
        return userEntityList.map(user -> objectMapper.convertValue(user, UserResponse.class));
    }
}
