package com.rodrigoguzman.spring_security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigoguzman.spring_security.dto.AuthLoginRequestDTO;
import com.rodrigoguzman.spring_security.dto.AuthResponseDTO;
import com.rodrigoguzman.spring_security.service.UserDetailsServiceImp;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final UserDetailsServiceImp userDetailsService;

    AuthenticationController(UserDetailsServiceImp userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthLoginRequestDTO userRequest) {
        return new ResponseEntity<>(this.userDetailsService.loginUser(userRequest), HttpStatus.OK);
    }

}
