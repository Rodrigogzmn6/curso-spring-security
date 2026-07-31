package com.rodrigoguzman.school_project.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SecuredUserResponseDTO(
        @NotNull @NotBlank String username,
        @NotNull Set<RoleResponseDTO> roles) {
}
