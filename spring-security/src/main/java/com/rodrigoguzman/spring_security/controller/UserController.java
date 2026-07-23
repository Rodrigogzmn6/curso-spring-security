package com.rodrigoguzman.spring_security.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigoguzman.spring_security.model.Role;
import com.rodrigoguzman.spring_security.model.UserSec;
import com.rodrigoguzman.spring_security.service.IRoleService;
import com.rodrigoguzman.spring_security.service.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    final IUserService userService;

    final IRoleService roleService;

    UserController(IUserService userService, IRoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('CREATE')")
    public ResponseEntity<UserSec> createUser(@RequestBody UserSec user) {
        Set<Role> rolesList = new HashSet<Role>();
        Role readRole;

        // Encrypt password
        user.setPassword(userService.encryptPassword(user.getPassword()));

        for (Role role : user.getRolesList()) {
            readRole = roleService.findById(role.getId()).orElse(null);

            if (readRole != null) {
                rolesList.add(readRole);
            }
        }

        if (!rolesList.isEmpty()) {
            user.setRolesList(rolesList);
            UserSec createdUser = userService.save(user);
            return ResponseEntity.ok(createdUser);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<List<UserSec>> getAllRoles() {
        List<UserSec> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<UserSec> getRoleById(@PathVariable Long id) {
        Optional<UserSec> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
