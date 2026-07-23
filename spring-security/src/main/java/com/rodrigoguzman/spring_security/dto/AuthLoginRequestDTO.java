package com.rodrigoguzman.spring_security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthLoginRequestDTO(
        @NotBlank @NotNull String username,
        @NotBlank @NotNull String password) {
}