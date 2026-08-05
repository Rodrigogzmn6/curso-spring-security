package com.rodrigoguzman.school_project.dto;

import java.util.Set;

import com.rodrigoguzman.school_project.model.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SecuredUserRequestDTO(
        @NotNull @NotBlank String username,
        @NotNull @NotBlank String password,
        @NotNull Set<Role> roles) {
}
