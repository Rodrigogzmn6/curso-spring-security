package com.rodrigoguzman.school_project.service;

import com.rodrigoguzman.school_project.dto.UserRegistryDTO;
import com.rodrigoguzman.school_project.dto.UserRegistryResponesDTO;

public interface IStudentService {
    UserRegistryResponesDTO createStudent(UserRegistryDTO user);

    void deleteStudent(Long id);
}
