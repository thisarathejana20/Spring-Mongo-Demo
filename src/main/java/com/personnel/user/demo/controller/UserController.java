package com.personnel.user.demo.controller;

import com.personnel.user.demo.dto.UserRequest;
import com.personnel.user.demo.dto.UserResponse;
import com.personnel.user.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public String create(@RequestBody UserRequest user) {
        return userService.create(user) ? "User created" : "User not created";
    }

    @GetMapping("")
    public Page<UserResponse> findAll(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return userService.findAll(page, size);
    }

    @GetMapping("/by-role")
    public Page<UserResponse> findAllByRole(@RequestParam String role,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return userService.findAllByRole(role, page, size);
    }
}
