package com.rodrigoguzman.school_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthLoginRequestDTO(
        @NotBlank @NotNull String username,
        @NotBlank @NotNull String password) {
}