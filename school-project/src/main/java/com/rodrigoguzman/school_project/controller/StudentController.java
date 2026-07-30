package com.rodrigoguzman.school_project.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigoguzman.school_project.dto.UserRegistryDTO;
import com.rodrigoguzman.school_project.dto.UserRegistryResponesDTO;
import com.rodrigoguzman.school_project.service.IStudentService;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {
    final IStudentService service;

    StudentController(IStudentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserRegistryResponesDTO> createStudent(@RequestBody UserRegistryDTO user) {
        return ResponseEntity.ok(service.createStudent(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        service.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
