package com.rodrigoguzman.school_project.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CourseResponseDTO(
        @NotNull @NotBlank String name,
        ProfessorDTO professor,
        Set<StudentDTO> students) {

}
