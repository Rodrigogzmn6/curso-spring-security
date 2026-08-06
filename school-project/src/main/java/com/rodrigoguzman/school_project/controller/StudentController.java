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
@RequestMapping("/api/v1/students")
@AllArgsConstructor
public class StudentController {
    final ISchoolUserService service;

    @PostMapping
    @PreAuthorize("hasRole('Administrator')")
    public ResponseEntity<SchoolUserResponseDTO> createStudent(@RequestBody SchoolUserRequestDTO user) {
        return ResponseEntity.ok(service.createSchoolUser(user, "Student"));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('R_Students')")
    public ResponseEntity<List<SchoolUserResponseDTO>> findAllStudents() {
        return ResponseEntity.ok(service.findAllSchoolUsersByRole("Student"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('R_Students')")
    public ResponseEntity<SchoolUserResponseDTO> findStudentsById(@PathVariable Long id) {
        return ResponseEntity
                .ok(service.findSchoolUserById(id, "Student")
                        .orElseThrow(() -> new RuntimeException("Student not found")));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Administrator')")
    public ResponseEntity<SchoolUserResponseDTO> updateStudent(@PathVariable Long id,
            @RequestBody SchoolUserRequestDTO user) {
        return ResponseEntity.ok(service.updateSchoolUser(id, user, "Student"));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Administrator')")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        service.deleteSchoolUser(id, "Student");
        return ResponseEntity.noContent().build();
    }
}
