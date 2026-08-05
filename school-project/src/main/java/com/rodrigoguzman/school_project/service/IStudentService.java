package com.rodrigoguzman.school_project.service;

import java.util.List;
import java.util.Optional;

import com.rodrigoguzman.school_project.dto.SchoolUserRequestDTO;
import com.rodrigoguzman.school_project.dto.SchoolUserResponseDTO;

public interface IStudentService {
    SchoolUserResponseDTO createStudent(SchoolUserRequestDTO student);

    List<SchoolUserResponseDTO> findAllStudents();

    Optional<SchoolUserResponseDTO> findStudentById(Long id);

    SchoolUserResponseDTO updateStudent(Long id, SchoolUserRequestDTO student);

    void deleteStudent(Long id);
}
