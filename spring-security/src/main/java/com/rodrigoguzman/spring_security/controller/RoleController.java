package com.rodrigoguzman.spring_security.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigoguzman.spring_security.model.Permission;
import com.rodrigoguzman.spring_security.model.Role;
import com.rodrigoguzman.spring_security.service.IPermissionService;
import com.rodrigoguzman.spring_security.service.IRoleService;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    @Autowired
    IRoleService roleService;

    @Autowired
    IPermissionService permissionService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('CREATE')")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Set<Permission> permissionsList = new HashSet<Permission>();
        Permission readPermission;

        for (Permission permission : role.getPermissionsList()) {
            readPermission = permissionService.findById(permission.getId()).orElse(null);

            if (readPermission != null) {
                permissionsList.add(readPermission);
            }
        }

        role.setPermissionsList(permissionsList);
        Role createdRole = roleService.save(role);
        return ResponseEntity.ok(createdRole);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Optional<Role> role = roleService.findById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody Role role) {
        return null;
    }
}
