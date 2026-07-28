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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigoguzman.school_project.model.Role;
import com.rodrigoguzman.school_project.model.SchoolUser;
import com.rodrigoguzman.school_project.service.IRoleService;
import com.rodrigoguzman.school_project.service.IUserService;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    final IUserService service;

    final IRoleService roleService;

    UserController(IUserService service, IRoleService roleService) {
        this.service = service;
        this.roleService = roleService;
    }

    @PostMapping()
    @PreAuthorize("hasRole('Administrator')")
    public ResponseEntity<SchoolUser> createUser(@RequestBody SchoolUser user) {
        Set<Role> rolesList = new HashSet<Role>();
        Role readRole;

        // Encrypt password
        user.setPassword(service.encryptPassword(user.getPassword()));

        for (Role role : user.getRolesList()) {
            readRole = roleService.findRoleById(role.getId()).orElse(null);

            if (readRole != null) {
                rolesList.add(readRole);
            }
        }

        if (!rolesList.isEmpty()) {
            user.setRolesList(rolesList);
            SchoolUser createdUser = service.createUser(user);
            return ResponseEntity.ok(createdUser);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('R_Users')")
    public ResponseEntity<List<SchoolUser>> getAllRoles() {
        List<SchoolUser> users = service.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('R_Users')")
    public ResponseEntity<SchoolUser> getRoleById(@PathVariable Long id) {
        Optional<SchoolUser> user = service.findUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('U_Users')")
    public ResponseEntity<SchoolUser> updateUser(@PathVariable Long id, @RequestBody SchoolUser user) {
        SchoolUser updatedUser = service.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('D_Users')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
