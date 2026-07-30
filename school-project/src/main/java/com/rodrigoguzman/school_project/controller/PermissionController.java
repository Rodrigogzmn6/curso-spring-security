package com.rodrigoguzman.school_project.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigoguzman.school_project.dto.PermissionResponseDTO;
import com.rodrigoguzman.school_project.model.Permission;
import com.rodrigoguzman.school_project.service.IPermissionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {
    final IPermissionService service;

    PermissionController(IPermissionService service) {
        this.service = service;
    }

    @PostMapping()
    // @PreAuthorize("hasRole('Administrator')")
    public ResponseEntity<PermissionResponseDTO> createPermission(@RequestBody Permission permission) {
        PermissionResponseDTO createdPermission = service.createPermission(permission);
        return ResponseEntity.ok(createdPermission);
    }

    @GetMapping
    // @PreAuthorize("hasAuthority('R_Permissions')")
    public ResponseEntity<List<PermissionResponseDTO>> getAllPermissions() {
        List<PermissionResponseDTO> foundPermissions = service.findAllPermissions();
        return ResponseEntity.ok(foundPermissions);
    }

    @GetMapping("/{id}")
    // @PreAuthorize("hasAuthority('R_Permissions')")
    public ResponseEntity<PermissionResponseDTO> getPermissionById(@PathVariable Long id) {
        Optional<PermissionResponseDTO> foundPermission = service.findPermissionById(id);
        return ResponseEntity.ok(foundPermission.orElse(null));
    }

    @PutMapping("/{id}")
    // @PreAuthorize("hasAuthority('U_Permissions')")
    public ResponseEntity<PermissionResponseDTO> updatePermission(@PathVariable Long id,
            @RequestBody Permission permission) {
        PermissionResponseDTO updatedPermission = service.updatePermission(id, permission);
        return ResponseEntity.ok(updatedPermission);
    }

    @DeleteMapping("/{id}")
    // @PreAuthorize("hasAuthority('D_Permissions')")
    public ResponseEntity<Void> deletePermission(@PathVariable Long id) {
        service.deletePermission(id);
        return ResponseEntity.noContent().build();
    }
}
