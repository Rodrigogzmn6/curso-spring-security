package com.rodrigoguzman.school_project.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.rodrigoguzman.school_project.dto.SchoolUserRequestDTO;
import com.rodrigoguzman.school_project.dto.SchoolUserResponseDTO;
import com.rodrigoguzman.school_project.model.Student;
import com.rodrigoguzman.school_project.repository.IRoleRepository;
import com.rodrigoguzman.school_project.repository.IStudentRepository;
import com.rodrigoguzman.school_project.repository.ISecuredUserRepository;

import jakarta.transaction.Transactional;

@Service
public class StudentService implements IStudentService {
    final IStudentRepository repository;
    final ISecuredUserService userService;
    final ISecuredUserRepository userRepository;
    final IRoleRepository roleRepository;

    StudentService(IStudentRepository repository, ISecuredUserService userService, IRoleRepository roleRepository,
            ISecuredUserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userService = userService;
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public SchoolUserResponseDTO createStudent(SchoolUserRequestDTO user) {
        user.setRoles(Set
                .of(roleRepository.findByRole("Student").orElseThrow(() -> new RuntimeException("Role not found"))));

        userService.createUser(user);

        repository.save(Student.builder()
                .name(user.getName())
                .dni(user.getDni())
                .schoolUser(userRepository.findSchoolUserEntityByUsername(user.getUsername())
                        .orElseThrow(() -> new RuntimeException("User not found")))
                .courses(user.getCourses())
                .build());

        return SchoolUserResponseDTO.builder()
                .username(user.getUsername())
                .name(user.getName())
                .dni(user.getDni())
                .roles(user.getRoles())
                .courses(user.getCourses())
                .build();
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Long userId = student.getSchoolUser().getId();
        repository.delete(student); // 1. remove student profile
        userService.deleteUser(userId); // 2. remove login account
    }
}
