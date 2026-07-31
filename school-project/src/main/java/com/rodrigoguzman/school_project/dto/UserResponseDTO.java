package com.rodrigoguzman.school_project.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserResponseDTO(
        @NotNull @NotBlank String username,
        @NotNull Set<RoleResponseDTO> rolesList) {
}
