package com.rodrigoguzman.school_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigoguzman.school_project.model.Professor;

public interface IProfessorRepository extends JpaRepository<Professor, Long> {

}
