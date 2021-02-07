package com.spring.project.service;

import com.spring.project.dto.RegistrationDto;
import com.spring.project.dto.UserDto;
import com.spring.project.dto.mapper.UserMapper;
import com.spring.project.exceptions.UserAlreadyExistException;
import com.spring.project.model.User;
import com.spring.project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    public User registerNewAccount(RegistrationDto regDto)
            throws UserAlreadyExistException {

        User user = mapper.userRegDtoToUser(regDto);

        if (emailExist(user.getEmail())) {
            throw new UserAlreadyExistException(
                    "There is an account with this email address: "
                            + user.getEmail());
        }
        // TODO: add password encoding
//        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email).orElse(null) != null;
    }
}
