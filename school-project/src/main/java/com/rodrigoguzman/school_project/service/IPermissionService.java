package com.rodrigoguzman.school_project.service;

import java.util.List;
import java.util.Optional;

import com.rodrigoguzman.school_project.dto.PermissionResponseDTO;
import com.rodrigoguzman.school_project.model.Permission;

public interface IPermissionService {
    PermissionResponseDTO createPermission(Permission permission);

    List<PermissionResponseDTO> findAllPermissions();

    Optional<PermissionResponseDTO> findPermissionById(Long id);

    PermissionResponseDTO updatePermission(Long id, Permission permission);

    void deletePermission(Long id);
}
