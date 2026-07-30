package com.rodrigoguzman.school_project.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rodrigoguzman.school_project.dto.PermissionResponseDTO;
import com.rodrigoguzman.school_project.model.Permission;
import com.rodrigoguzman.school_project.repository.IPermissionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PermissionService implements IPermissionService {
    final IPermissionRepository repository;

    @Override
    public PermissionResponseDTO createPermission(Permission permission) {
        repository.save(permission);

        return PermissionResponseDTO.builder()
                .permission(permission.getPermission())
                .build();
    }

    @Override
    public List<PermissionResponseDTO> findAllPermissions() {
        return repository.findAll().stream()
                .map(permission -> PermissionResponseDTO.builder()
                        .permission(permission.getPermission())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PermissionResponseDTO> findPermissionById(Long id) {
        Optional<Permission> foundPermission = repository.findById(id);

        if (foundPermission.isPresent()) {
            return Optional.of(PermissionResponseDTO.builder()
                    .permission(foundPermission.get().getPermission())
                    .build());
        }

        return null;
    }

    @Override
    public PermissionResponseDTO updatePermission(Long id, Permission permission) {
        Permission permissionToEdit = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        permissionToEdit.setPermission(
                permission.getPermission() != null && !permission.getPermission().isEmpty()
                        ? permission.getPermission()
                        : permissionToEdit.getPermission());

        repository.save(permissionToEdit);

        return PermissionResponseDTO.builder()
                .permission(permissionToEdit.getPermission())
                .build();
    }

    @Override
    public void deletePermission(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permission not found"));

        repository.deleteById(id);
    }
}
