package com.rodrigoguzman.school_project.dto;

import java.util.Set;

import com.rodrigoguzman.school_project.model.Course;
import com.rodrigoguzman.school_project.model.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolUserRequestDTO {
    private String username, password, name, dni;
    private Set<Role> roles;
    private Set<Course> courses;
}
