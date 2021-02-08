package com.spring.project.service;

import com.spring.project.dto.LoginDto;
import com.spring.project.dto.RegistrationDto;
import com.spring.project.dto.UserDto;
import com.spring.project.dto.mapper.UserMapper;
import com.spring.project.exceptions.CredentialsException;
import com.spring.project.exceptions.UserAlreadyExistException;
import com.spring.project.model.User;
import com.spring.project.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Log4j2
@Service
public class UserService implements UserDetailsService {
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

        User user = mapper.regDtoToUser(regDto);

        if (emailExist(user.getEmail())) {
            log.warn("Trying to register a new account {}: There is an account with this email address",
                    user.getEmail());
            throw new UserAlreadyExistException("reg.login_not_unique");
        }
        // TODO: add password encoding
//        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email).orElse(null) != null;
    }

    public User getUser(LoginDto loginDto) throws CredentialsException {
        User user = userRepository
                .findByUserEmailAndPassword(loginDto.getEmail(), loginDto.getPassword())
                .orElseThrow(() -> new CredentialsException("Invalid credentials"));
        log.info("{} successfully logged in", loginDto.getEmail());
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(email));
    }
}
