package com.kameleoon.user.service;


import com.kameleoon.user.dto.user.UserCreateDto;
import com.kameleoon.user.dto.user.UserDto;
import com.kameleoon.user.entity.user.User;
import com.kameleoon.user.mappers.user.UserCreateMapper;
import com.kameleoon.user.mappers.user.UserMapper;
import com.kameleoon.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        userRepository.save(user);
        return userMapper.toDto(user);
    }

}
