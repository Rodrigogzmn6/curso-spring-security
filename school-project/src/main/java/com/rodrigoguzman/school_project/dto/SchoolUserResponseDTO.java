package com.rodrigoguzman.school_project.dto;

import java.util.Set;

import com.rodrigoguzman.school_project.model.Course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SchoolUserResponseDTO(
        @NotNull @NotBlank String username,
        @NotNull @NotBlank String name,
        @NotNull @NotBlank String dni,
        @NotNull Set<RoleResponseDTO> roles,
        @NotNull Set<Course> courses) {
}
