package com.rodrigoguzman.school_project.service;

import java.util.List;
import java.util.Optional;

import com.rodrigoguzman.school_project.dto.UserRegistryDTO;
import com.rodrigoguzman.school_project.dto.UserRegistryResponesDTO;
import com.rodrigoguzman.school_project.model.SchoolUser;

public interface IUserService {
    UserRegistryResponesDTO createUser(UserRegistryDTO user);

    List<SchoolUser> findAllUsers();

    Optional<SchoolUser> findUserById(Long id);

    Optional<SchoolUser> findUserByUsername(String username);

    SchoolUser updateUser(Long id, SchoolUser user);

    void deleteUser(Long id);

    String encryptPassword(String password);
}
