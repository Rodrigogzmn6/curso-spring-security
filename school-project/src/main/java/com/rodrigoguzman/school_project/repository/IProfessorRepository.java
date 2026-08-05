package com.rodrigoguzman.school_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigoguzman.school_project.model.Professor;

public interface IProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByName(String name);

    Optional<Professor> findByDni(String dni);
}
