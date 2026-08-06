package com.rodrigoguzman.school_project.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rodrigoguzman.school_project.dto.CourseRequestDTO;
import com.rodrigoguzman.school_project.dto.CourseResponseDTO;
import com.rodrigoguzman.school_project.service.ICourseService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/courses")
@AllArgsConstructor
public class CourseController {

    final ICourseService service;

    @PostMapping()
    public ResponseEntity<CourseResponseDTO> createCourse(@RequestBody CourseRequestDTO course) {
        return ResponseEntity.ok(service.createCourse(course));
    }

    @GetMapping()
    public ResponseEntity<List<CourseResponseDTO>> findAllCourses() {
        return ResponseEntity.ok(service.findAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> findCourseById(@PathVariable Long id) {
        return ResponseEntity
                .ok(service.findCourseById(id).orElseThrow(() -> new RuntimeException("Course not found")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDTO> updateCourse(@PathVariable Long id, @RequestBody CourseRequestDTO course) {
        return ResponseEntity.ok(service.updateCourse(id, course));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        service.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

}
