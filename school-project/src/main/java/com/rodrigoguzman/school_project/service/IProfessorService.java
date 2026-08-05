package com.rodrigoguzman.school_project.service;

import java.util.List;
import java.util.Optional;

import com.rodrigoguzman.school_project.dto.SchoolUserRequestDTO;
import com.rodrigoguzman.school_project.dto.SchoolUserResponseDTO;

public interface IProfessorService {
    SchoolUserResponseDTO createProfessor(SchoolUserRequestDTO student);

    List<SchoolUserResponseDTO> findAllProfessors();

    Optional<SchoolUserResponseDTO> findProfessorById(Long id);

    SchoolUserResponseDTO updateProfessor(Long id, SchoolUserRequestDTO student);

    void deleteProfessor(Long id);
}
