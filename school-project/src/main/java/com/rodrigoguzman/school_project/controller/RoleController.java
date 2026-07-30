package com.rodrigoguzman.school_project.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigoguzman.school_project.dto.RoleResponseDTO;
import com.rodrigoguzman.school_project.model.Role;
import com.rodrigoguzman.school_project.service.IPermissionService;
import com.rodrigoguzman.school_project.service.IRoleService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {
    final IRoleService service;

    final IPermissionService permissionService;

    RoleController(IRoleService service, IPermissionService permissionService) {
        this.service = service;
        this.permissionService = permissionService;
    }

    @PostMapping()
    @PreAuthorize("hasRole('Administrator')")
    public ResponseEntity<RoleResponseDTO> createRole(@RequestBody Role role) {
        RoleResponseDTO createdRole = service.createRole(role);

        return ResponseEntity.ok(createdRole);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('R_Roles')")
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        List<RoleResponseDTO> foundRoles = service.findAllRoles();
        return ResponseEntity.ok(foundRoles);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('R_Roles')")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Long id) {
        Optional<RoleResponseDTO> foundRole = service.findRoleById(id);
        return ResponseEntity.ok(foundRole.orElse(null));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('U_Roles')")
    public ResponseEntity<RoleResponseDTO> updateRole(@PathVariable Long id, @RequestBody Role role) {
        RoleResponseDTO updatedRole = service.updateRole(id, role);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('D_Roles')")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        service.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
