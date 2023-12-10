package com.kameleoon.user.controller;

import com.kameleoon.user.dto.user.UserCreateDto;
import com.kameleoon.user.dto.user.UserDto;
import com.kameleoon.user.service.UserService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/get{userId}")
    public UserDto get(@PathVariable @Min(0) Long userId) {
        return userService.get(userId);
    }
}
