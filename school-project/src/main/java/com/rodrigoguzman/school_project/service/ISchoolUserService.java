package com.rodrigoguzman.school_project.service;

import java.util.List;
import java.util.Optional;

import com.rodrigoguzman.school_project.dto.SchoolUserRequestDTO;
import com.rodrigoguzman.school_project.dto.SchoolUserResponseDTO;

public interface ISchoolUserService {
    SchoolUserResponseDTO createSchoolUser(SchoolUserRequestDTO schoolUser, String role);

    List<SchoolUserResponseDTO> findAllSchoolUsers();

    List<SchoolUserResponseDTO> findAllSchoolUsersByRole(String role);

    Optional<SchoolUserResponseDTO> findSchoolUserById(Long id, String role);

    SchoolUserResponseDTO updateSchoolUser(Long id, SchoolUserRequestDTO schoolUser, String role);

    void deleteSchoolUser(Long id, String role);
}
