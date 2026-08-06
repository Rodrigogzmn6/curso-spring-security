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

import com.rodrigoguzman.school_project.dto.SchoolUserRequestDTO;
import com.rodrigoguzman.school_project.dto.SchoolUserResponseDTO;
import com.rodrigoguzman.school_project.service.ISchoolUserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1/professors")
@AllArgsConstructor
public class ProfessorController {
    final ISchoolUserService service;

    @PostMapping
    @PreAuthorize("hasRole('Administrator')")
    public ResponseEntity<SchoolUserResponseDTO> createStudent(@RequestBody SchoolUserRequestDTO user) {
        return ResponseEntity.ok(service.createSchoolUser(user, "Professor"));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('R_Professors')")
    public ResponseEntity<List<SchoolUserResponseDTO>> findAllStudents() {
        return ResponseEntity.ok(service.findAllSchoolUsersByRole("Professor"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('R_Professors')")
    public ResponseEntity<SchoolUserResponseDTO> findStudentsById(@PathVariable Long id) {
        return ResponseEntity
                .ok(service.findSchoolUserById(id, "Professor")
                        .orElseThrow(() -> new RuntimeException("Professor not found")));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Administrator')")
    public ResponseEntity<SchoolUserResponseDTO> updateStudent(@PathVariable Long id,
            @RequestBody SchoolUserRequestDTO user) {
        return ResponseEntity.ok(service.updateSchoolUser(id, user, "Professor"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Administrator')")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        service.deleteSchoolUser(id, "Professor");
        return ResponseEntity.noContent().build();
    }
}
