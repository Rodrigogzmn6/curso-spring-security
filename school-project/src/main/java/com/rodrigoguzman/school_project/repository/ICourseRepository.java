package com.rodrigoguzman.school_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigoguzman.school_project.model.Course;

public interface ICourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByName(String name);
}
