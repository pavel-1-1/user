package com.kameleoon.user.service;


import com.kameleoon.user.dto.user.UserCreateDto;
import com.kameleoon.user.dto.user.UserDto;
import com.kameleoon.user.entity.user.User;
import com.kameleoon.user.global_exception.RequestError;
import com.kameleoon.user.global_exception.ResourceNotFoundException;
import com.kameleoon.user.mappers.user.UserCreateMapper;
import com.kameleoon.user.mappers.user.UserMapper;
import com.kameleoon.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserCreateMapper userCreateMapper;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, UserCreateMapper userCreateMapper,
                       UserMapper userMapper, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userCreateMapper = userCreateMapper;
        this.userMapper = userMapper;
        this.encoder = encoder;
    }

    public UserDto create(UserCreateDto dto) {
        User user = userCreateMapper.toEntity(dto);
        user.setPassword(encoder.encode(dto.getPassword()));
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new RequestError("Such a user is registered!");
        }
        return userMapper.toDto(user);
    }

    public UserDto get(Long userId) {
        return userMapper.toDto(userRepository.findById(userId).orElseThrow(() ->
                new ResourceNotFoundException("The user was not found")));
    }
}
