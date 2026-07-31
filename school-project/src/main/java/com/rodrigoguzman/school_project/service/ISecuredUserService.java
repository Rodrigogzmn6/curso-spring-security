package com.rodrigoguzman.school_project.service;

import java.util.List;
import java.util.Optional;

import com.rodrigoguzman.school_project.dto.SecuredUserRequestDTO;
import com.rodrigoguzman.school_project.dto.SecuredUserResponseDTO;

public interface ISecuredUserService {
    SecuredUserResponseDTO createSecuredUser(SecuredUserRequestDTO user);

    List<SecuredUserResponseDTO> findAllSecuredUsers();

    Optional<SecuredUserResponseDTO> findSecuredUserById(Long id);

    Optional<SecuredUserResponseDTO> findSecuredUserByUsername(String username);

    SecuredUserResponseDTO updateSecuredUser(Long id, SecuredUserRequestDTO user);

    void deleteSecuredUser(Long id);

    String encryptPassword(String password);
}
