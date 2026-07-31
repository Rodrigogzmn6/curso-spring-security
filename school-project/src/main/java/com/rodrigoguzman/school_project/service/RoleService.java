package com.rodrigoguzman.school_project.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rodrigoguzman.school_project.dto.RoleResponseDTO;
import com.rodrigoguzman.school_project.model.Permission;
import com.rodrigoguzman.school_project.model.Role;
import com.rodrigoguzman.school_project.repository.IPermissionRepository;
import com.rodrigoguzman.school_project.repository.IRoleRepository;
import com.rodrigoguzman.school_project.utils.PermissionsUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService implements IRoleService {
    private final IRoleRepository repository;
    private final IPermissionRepository permissionRepository;

    @Override
    public RoleResponseDTO createRole(Role role) {
        Set<Permission> permissionsList = new HashSet<>();

        for (Permission p : role.getPermissionsList()) {
            Permission foundPermission = permissionRepository.findByPermission(p.getPermission())
                    .orElseThrow(() -> new RuntimeException("Permission not found"));
            permissionsList.add(foundPermission);
        }

        role.setPermissionsList(permissionsList);

        repository.save(role);

        return RoleResponseDTO.builder()
                .role(role.getRole())
                .permissionsList(PermissionsUtils.convertPermissionsToDTO(permissionsList))
                .build();
    }

    @Override
    public List<RoleResponseDTO> findAllRoles() {
        return repository.findAll().stream()
                .map(role -> RoleResponseDTO.builder()
                        .role(role.getRole())
                        .permissionsList(PermissionsUtils.convertPermissionsToDTO(role.getPermissionsList()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RoleResponseDTO> findRoleById(Long id) {
        Optional<Role> foundRole = repository.findById(id);

        if (foundRole.isPresent()) {
            return Optional.of(RoleResponseDTO.builder()
                    .role(foundRole.get().getRole())
                    .permissionsList(PermissionsUtils.convertPermissionsToDTO(foundRole.get().getPermissionsList()))
                    .build());
        }

        return null;
    }

    @Override
    public RoleResponseDTO updateRole(Long id, Role role) {
        Set<Permission> permissionsList = new HashSet<>();

        for (Permission p : role.getPermissionsList()) {
            Permission foundPermission = permissionRepository.findByPermission(p.getPermission())
                    .orElseThrow(() -> new RuntimeException("Permission not found"));
            permissionsList.add(foundPermission);
        }

        Role roleToEdit = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        roleToEdit.setRole(role.getRole() != null && !role.getRole().isEmpty()
                ? role.getRole()
                : roleToEdit.getRole());

        roleToEdit.setPermissionsList(permissionsList);

        repository.save(roleToEdit);

        return RoleResponseDTO.builder()
                .role(roleToEdit.getRole())
                .permissionsList(PermissionsUtils.convertPermissionsToDTO(permissionsList))
                .build();
    }

    @Override
    public void deleteRole(Long id) {
        repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        repository.deleteById(id);
    }
}
