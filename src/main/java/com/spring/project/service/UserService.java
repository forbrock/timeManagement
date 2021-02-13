package com.spring.project.service;

import com.spring.project.dto.LoginDto;
import com.spring.project.dto.RegistrationDto;
import com.spring.project.dto.UpdateUserDto;
import com.spring.project.dto.UserDto;
import com.spring.project.dto.mapper.UserMapper;
import com.spring.project.exceptions.CredentialsException;
import com.spring.project.exceptions.UserAlreadyExistException;
import com.spring.project.model.Activity;
import com.spring.project.model.User;
import com.spring.project.model.UserActivity;
import com.spring.project.model.enums.ActivityState;
import com.spring.project.model.enums.Role;
import com.spring.project.repository.ActivityRepository;
import com.spring.project.repository.UserActivityRepository;
import com.spring.project.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Log4j2
@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper mapper;
    private PasswordEncoder passwordEncoder;
    private SecurityService securityService;
    private ActivityRepository activityRepository;
    private UserActivityRepository userActivityRepository;
    private ModelAndView mav;

    @Autowired
    public UserService(UserRepository userRepository,
                       UserMapper mapper,
                       PasswordEncoder passwordEncoder,
                       SecurityService securityService,
                       ActivityRepository activityRepository,
                       UserActivityRepository userActivityRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.securityService = securityService;
        this.activityRepository = activityRepository;
        this.userActivityRepository = userActivityRepository;
    }

    public List<UserDto> getAllUsers() {
        return mapper.mapUserToUserDto(userRepository.findAll());
    }

    @Transactional
    public User registerNewAccount(RegistrationDto regDto)
            throws UserAlreadyExistException {
        User user = mapper.regDtoToUser(regDto);

        if (emailExist(user.getEmail())) {
            log.warn("Trying to register a new account {}: " +
                            "There is an account with this email address", user.getEmail());
            throw new UserAlreadyExistException("reg.login.not.unique");
        }

        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(regDto.getPassword()));
        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmail(email).orElse(null) != null;
    }

    public User getUser(LoginDto loginDto) throws CredentialsException {
        User user = userRepository
                .findByEmailAndPassword(loginDto.getEmail(), loginDto.getPassword())
                .orElseThrow(() -> new CredentialsException("valid.login.wrong.credential"));
        log.info("User {} successfully logged in", loginDto.getEmail());
        return user;
    }

    // TODO: don't forget to remove id from exception output
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new UsernameNotFoundException("No such user was found, id: " + id));
    }

    // TODO: don't forget to remove id from exception output
    @Transactional
    public User update(UpdateUserDto updateUserDto) {
        User user = userRepository.findById(updateUserDto.getId()).orElseThrow(() ->
                new UsernameNotFoundException("No such user with id: " + updateUserDto.getId()));
        user.setFirstName(updateUserDto.getFirstName());
        user.setLastName(updateUserDto.getLastName());
        return userRepository.save(user);
    }

    public Long deleteById(long id) {
        try {
            userRepository.deleteById(id);
        } catch (NoSuchElementException e) {
            log.info("Deleted user with id: " + id);
        }
        return id;
    }
}
