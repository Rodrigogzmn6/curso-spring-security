package com.rodrigoguzman.school_project.dto;

import java.util.Set;

import com.rodrigoguzman.school_project.model.Professor;
import com.rodrigoguzman.school_project.model.Student;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CourseRequestDTO(
        @NotNull @NotBlank String name,
        Professor professor,
        Set<Student> students) {
}
