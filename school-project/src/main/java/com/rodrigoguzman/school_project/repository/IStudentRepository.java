package com.rodrigoguzman.school_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigoguzman.school_project.model.Student;

public interface IStudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByName(String name);

    Optional<Student> findByDni(String dni);
}
