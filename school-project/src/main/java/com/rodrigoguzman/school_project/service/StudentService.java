package com.rodrigoguzman.school_project.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.rodrigoguzman.school_project.dto.SchoolUserRequestDTO;
import com.rodrigoguzman.school_project.dto.SchoolUserResponseDTO;
import com.rodrigoguzman.school_project.dto.SecuredUserRequestDTO;
import com.rodrigoguzman.school_project.model.Role;
import com.rodrigoguzman.school_project.model.SecuredUser;
import com.rodrigoguzman.school_project.model.Student;
import com.rodrigoguzman.school_project.repository.IRoleRepository;
import com.rodrigoguzman.school_project.repository.IStudentRepository;
import com.rodrigoguzman.school_project.utils.RolesUtils;
import com.rodrigoguzman.school_project.repository.ISecuredUserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentService implements IStudentService {
    final IStudentRepository repository;
    final ISecuredUserService userService;
    final IRoleRepository roleRepository;
    final ISecuredUserRepository userRepository;

    @Override
    @Transactional
    public SchoolUserResponseDTO createStudent(SchoolUserRequestDTO student) {
        Set<Role> studentRole = Set
                .of(roleRepository.findByRole("Student")
                        .orElse(roleRepository.save(Role.builder().role("Student").build())));

        SecuredUserRequestDTO studentUser = SecuredUserRequestDTO.builder()
                .username(student.username())
                .password(student.password())
                .roles(studentRole)
                .build();

        userService.createSecuredUser(studentUser);

        Optional<SecuredUser> securedUser = userRepository
                .findByUsername(studentUser.username());

        repository.save(Student.builder()
                .name(student.name())
                .dni(student.dni())
                .schoolUser(
                        securedUser.orElseThrow(() -> new RuntimeException("User not found")))
                .courses(student.courses())
                .build());

        return SchoolUserResponseDTO.builder()
                .username(student.username())
                .name(student.name())
                .dni(student.dni())
                .roles(RolesUtils.convertRolesToDTO(studentRole))
                .courses(student.courses())
                .build();
    }

    @Override
    public List<SchoolUserResponseDTO> findAllStudents() {
        return repository.findAll().stream()
                .map(student -> SchoolUserResponseDTO.builder()
                        .username(student.getSchoolUser().getUsername())
                        .name(student.getName())
                        .dni(student.getDni())
                        .courses(student.getCourses())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SchoolUserResponseDTO> findStudentById(Long id) {
        Optional<Student> foundStudent = repository.findById(id);

        if (foundStudent.isPresent()) {
            return Optional.of(SchoolUserResponseDTO.builder()
                    .username(foundStudent.get().getSchoolUser().getUsername())
                    .name(foundStudent.get().getName())
                    .dni(foundStudent.get().getDni())
                    .courses(foundStudent.get().getCourses())
                    .build());
        }

        return Optional.empty();
    }

    @Override
    public SchoolUserResponseDTO updateStudent(Long id, SchoolUserRequestDTO student) {
        Set<Role> rolesList = new HashSet<>();

        for (Role r : student.roles()) {
            Role foundRole = roleRepository.findByRole(r.getRole())
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            rolesList.add(foundRole);
        }

        // TODO: Chequear que existan los cursos

        Student studentToEdit = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        studentToEdit.setName(student.name() != null && !student.name().isEmpty()
                ? student.name()
                : studentToEdit.getName());
        studentToEdit.setDni(student.dni() != null && !student.dni().isEmpty()
                ? student.dni()
                : studentToEdit.getDni());
        studentToEdit.setCourses(student.courses());

        repository.save(studentToEdit);

        return SchoolUserResponseDTO.builder()
                .username(studentToEdit.getSchoolUser().getUsername())
                .name(studentToEdit.getName())
                .dni(studentToEdit.getDni())
                .courses(studentToEdit.getCourses())
                .roles(RolesUtils.convertRolesToDTO(rolesList))
                .build();
    }

    @Override
    public void deleteStudent(Long id) {
        Student studentToDelete = repository.findById(id).orElseThrow(() -> new RuntimeException("Student not found"));

        if (studentToDelete != null) {
            repository.deleteById(id);
            userService.deleteSecuredUser(studentToDelete.getSchoolUser().getId());
        }
    }
}
