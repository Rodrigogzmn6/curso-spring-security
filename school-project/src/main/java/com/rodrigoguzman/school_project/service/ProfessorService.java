package com.rodrigoguzman.school_project.service;

import org.springframework.stereotype.Service;

import com.rodrigoguzman.school_project.model.Professor;
import com.rodrigoguzman.school_project.repository.IProfessorRepository;

@Service
public class ProfessorService implements IProfessorService {
    final IProfessorRepository repository;

    ProfessorService(IProfessorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Professor createProfessor(Professor professor) {
        return repository.save(professor);
    }
}
