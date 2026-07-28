package com.rodrigoguzman.school_project.service;

import java.util.List;
import java.util.Optional;

import com.rodrigoguzman.school_project.model.Permission;

public interface IPermissionService {
    Permission createPermission(Permission permission);

    List<Permission> findAllPermissions();

    Optional<Permission> findPermissionById(Long id);

    Permission updatePermission(Long id, Permission permission);

    void deletePermission(Long id);
}
