package com.rodrigoguzman.school_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseDTO(
        @NotNull @NotBlank String name,
        String professorName) {

}
