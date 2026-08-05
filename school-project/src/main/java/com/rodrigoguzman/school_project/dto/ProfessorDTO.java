package com.rodrigoguzman.school_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ProfessorDTO(
        @NotNull @NotBlank String name,
        @NotNull @NotBlank String dni) {

}
