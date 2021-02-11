package com.spring.project.service;

import com.spring.project.model.User;
import com.spring.project.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class SecurityService implements UserDetailsService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;

    @Autowired
    public SecurityService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentLoggedUser() {
        return Optional.ofNullable((User) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal())
                .orElseThrow();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("Not found such user: " + email));
    }

/*
    public void autoLogin(String email, String password) {
        UserDetails userDetails = loadUserByUsername(email);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(authenticationToken);

        if (authenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            log.debug("{} successfully auto logged in", email);
        }
    }
*/
}
