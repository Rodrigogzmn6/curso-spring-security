package com.rodrigoguzman.school_project.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigoguzman.school_project.model.Permission;
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
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Set<Permission> permissionsList = new HashSet<Permission>();
        Permission readPermission;

        for (Permission permission : role.getPermissionsList()) {
            readPermission = permissionService.findPermissionById(permission.getId()).orElse(null);

            if (readPermission != null) {
                permissionsList.add(readPermission);
            }
        }

        role.setPermissionsList(permissionsList);
        Role createdRole = service.createRole(role);
        return ResponseEntity.ok(createdRole);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('R_Roles')")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = service.findAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('R_Roles')")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Optional<Role> role = service.findRoleById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('U_Roles')")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role role) {
        service.updateRole(id, role);
        return ResponseEntity.ok(role);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('D_Roles')")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        service.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}
