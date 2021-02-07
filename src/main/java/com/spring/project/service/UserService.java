package com.spring.project.service;

import com.spring.project.dto.UserDto;
import com.spring.project.dto.mapper.UserMapper;
import com.spring.project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper mapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public List<UserDto> getAllUsers() {
        return mapper.mapUserToUserDto(userRepository.findAll());
    }
}
