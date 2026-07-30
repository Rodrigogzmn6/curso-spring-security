package com.rodrigoguzman.school_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigoguzman.school_project.model.Student;

public interface IStudentRepository extends JpaRepository<Student, Long> {

}
