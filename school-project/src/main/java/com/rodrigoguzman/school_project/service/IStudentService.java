package com.rodrigoguzman.school_project.service;

import com.rodrigoguzman.school_project.dto.SchoolUserRequestDTO;
import com.rodrigoguzman.school_project.dto.SchoolUserResponseDTO;

public interface IStudentService {
    SchoolUserResponseDTO createStudent(SchoolUserRequestDTO user);

    void deleteStudent(Long id);
}
