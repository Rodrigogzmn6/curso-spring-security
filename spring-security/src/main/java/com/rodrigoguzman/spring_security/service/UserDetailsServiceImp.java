package com.rodrigoguzman.spring_security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rodrigoguzman.spring_security.dto.AuthLoginRequestDTO;
import com.rodrigoguzman.spring_security.dto.AuthResponseDTO;
import com.rodrigoguzman.spring_security.model.UserSec;
import com.rodrigoguzman.spring_security.repository.IUserRepository;
import com.rodrigoguzman.spring_security.utils.JwtUtils;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    final IUserRepository userRepository;

    private final JwtUtils jwtUtils;

    private final PasswordEncoder passwordEncoder;

    UserDetailsServiceImp(IUserRepository userRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // UserSec to UserDetails
        // Get UserSec from database
        UserSec userSec = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username " + username + " not found."));

        // Create Permissions list
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        // Get Roles y change them to SimpleGrantedAuthority
        userSec.getRolesList().stream()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole()))));

        // Get Permissions y change them to SimpleGrantedAuthority
        userSec.getRolesList().stream()
                .flatMap(role -> role.getPermissionsList().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getPermission())));

        // Return user in Spring User format
        return new User(
                userSec.getUsername(),
                userSec.getPassword(),
                userSec.isEnabled(),
                userSec.isAccountNotExpired(),
                userSec.isAccountNotLocked(),
                userSec.isCredentialNotExpired(),
                authorityList);
    }

    public AuthResponseDTO loginUser(AuthLoginRequestDTO userRequest) {
        String username = userRequest.username();
        String password = userRequest.password();

        Authentication authentication = this.authenticate(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);

        AuthResponseDTO response = new AuthResponseDTO(username, "Login successful", accessToken, true);

        return response;
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(),
                userDetails.getAuthorities());
    }
}