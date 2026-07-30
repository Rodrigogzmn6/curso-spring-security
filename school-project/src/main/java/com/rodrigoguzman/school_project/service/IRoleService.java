package com.rodrigoguzman.school_project.service;

import java.util.List;
import java.util.Optional;

import com.rodrigoguzman.school_project.dto.RoleResponseDTO;
import com.rodrigoguzman.school_project.model.Role;

public interface IRoleService {
    RoleResponseDTO createRole(Role role);

    List<RoleResponseDTO> findAllRoles();

    Optional<RoleResponseDTO> findRoleById(Long id);

    RoleResponseDTO updateRole(Long id, Role role);

    void deleteRole(Long id);
}
