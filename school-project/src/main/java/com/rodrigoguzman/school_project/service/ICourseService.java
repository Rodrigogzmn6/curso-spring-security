package com.rodrigoguzman.school_project.service;

import java.util.List;
import java.util.Optional;

import com.rodrigoguzman.school_project.dto.CourseRequestDTO;
import com.rodrigoguzman.school_project.dto.CourseResponseDTO;

public interface ICourseService {
    CourseResponseDTO createCourse(CourseRequestDTO course);

    List<CourseResponseDTO> findAllCourses();

    Optional<CourseResponseDTO> findCourseById(Long id);

    CourseResponseDTO updateCourse(Long id, CourseRequestDTO course);

    void deleteCourse(Long id);

}
