package com.rodrigoguzman.school_project.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.rodrigoguzman.school_project.model.Permission;
import com.rodrigoguzman.school_project.model.Role;
import com.rodrigoguzman.school_project.repository.IRoleRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService implements IRoleService {
    private final IRoleRepository repository;

    @Override
    public Role createRole(Role role) {
        return repository.save(role);
    }

    @Override
    public List<Role> findAllRoles() {
        return repository.findAll();
    }

    @Override
    public Optional<Role> findRoleById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Role updateRole(Long id, Role role) {
        Role roleToEdit = findRoleById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        String newRole = role.getRole() != null && !role.getRole().isEmpty() ? role.getRole() : roleToEdit.getRole();
        Set<Permission> newPermissionsList = role.getPermissionsList() != null ? role.getPermissionsList()
                : roleToEdit.getPermissionsList();

        Role updatedRole = Role.builder()
                .id(roleToEdit.getId())
                .role(newRole)
                .permissionsList(newPermissionsList)
                .build();

        repository.save(updatedRole);
        return updatedRole;
    }

    @Override
    public void deleteRole(Long id) {
        Role roleToEdit = findRoleById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        repository.deleteById(roleToEdit.getId());
    }
}
