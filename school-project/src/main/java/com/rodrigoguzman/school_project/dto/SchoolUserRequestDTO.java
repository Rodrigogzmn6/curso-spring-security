package com.rodrigoguzman.school_project.dto;

import java.util.Set;

import com.rodrigoguzman.school_project.model.Course;
import com.rodrigoguzman.school_project.model.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SchoolUserRequestDTO(
        @NotNull @NotBlank String username,
        @NotNull @NotBlank String password,
        @NotNull @NotBlank String name,
        @NotNull @NotBlank String dni,
        @NotNull Set<Role> roles,
        @NotNull Set<Course> courses) {
}
