package com.rodrigoguzman.school_project.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rodrigoguzman.school_project.model.Permission;
import com.rodrigoguzman.school_project.repository.IPermissionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PermissionService implements IPermissionService {
    final IPermissionRepository repository;

    @Override
    public Permission createPermission(Permission permission) {
        return repository.save(permission);
    }

    @Override
    public List<Permission> findAllPermissions() {
        return repository.findAll();
    }

    @Override
    public Optional<Permission> findPermissionById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Permission updatePermission(Long id, Permission permission) {
        Permission permissionToEdit = findPermissionById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        String newPermission = permission.getPermission() != null && !permission.getPermission().isEmpty()
                ? permission.getPermission()
                : permissionToEdit.getPermission();

        Permission updatedPermission = Permission.builder()
                .id(permissionToEdit.getId())
                .permission(newPermission)
                .build();

        repository.save(updatedPermission);
        return updatedPermission;
    }

    @Override
    public void deletePermission(Long id) {
        Permission permissionToEdit = findPermissionById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        repository.deleteById(permissionToEdit.getId());
    }
}
