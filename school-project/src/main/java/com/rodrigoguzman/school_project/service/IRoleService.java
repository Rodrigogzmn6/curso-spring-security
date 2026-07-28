package com.rodrigoguzman.school_project.service;

import java.util.List;
import java.util.Optional;

import com.rodrigoguzman.school_project.model.Role;

public interface IRoleService {
    Role createRole(Role role);

    List<Role> findAllRoles();

    Optional<Role> findRoleById(Long id);

    Role updateRole(Long id, Role role);

    void deleteRole(Long id);
}
