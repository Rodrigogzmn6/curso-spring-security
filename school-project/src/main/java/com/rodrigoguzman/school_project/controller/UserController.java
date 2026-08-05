package com.rodrigoguzman.school_project.controller;

import java.util.List;

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

import com.rodrigoguzman.school_project.dto.SecuredUserRequestDTO;
import com.rodrigoguzman.school_project.dto.SecuredUserResponseDTO;
import com.rodrigoguzman.school_project.service.IRoleService;
import com.rodrigoguzman.school_project.service.ISecuredUserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    final ISecuredUserService service;
    final IRoleService roleService;

    @PostMapping()
    @PreAuthorize("hasRole('Administrator')")
    public ResponseEntity<SecuredUserResponseDTO> createUser(@RequestBody SecuredUserRequestDTO user) {
        return ResponseEntity.ok(service.createSecuredUser(user));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('R_Users')")
    public ResponseEntity<List<SecuredUserResponseDTO>> getAllSecuredUsers() {
        List<SecuredUserResponseDTO> users = service.findAllSecuredUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('R_Users')")
    public ResponseEntity<SecuredUserResponseDTO> getSecuredUserById(@PathVariable Long id) {
        SecuredUserResponseDTO user = service.findSecuredUserById(id).orElse(null);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('U_Users')")
    public ResponseEntity<SecuredUserResponseDTO> updateSecuredUser(@PathVariable Long id,
            @RequestBody SecuredUserRequestDTO user) {
        return ResponseEntity.ok(service.updateSecuredUser(id, user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('D_Users')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.deleteSecuredUser(id);
        return ResponseEntity.noContent().build();
    }
}
