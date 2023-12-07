package com.kameleoon.user.controller;

import com.kameleoon.user.dto.user.UserCreateDto;
import com.kameleoon.user.dto.user.UserDto;
import com.kameleoon.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public UserDto create(@Validated @RequestBody UserCreateDto dto) {
        return userService.create(dto);
    }
}
