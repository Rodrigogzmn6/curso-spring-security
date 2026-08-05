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
import com.rodrigoguzman.school_project.model.Professor;
import com.rodrigoguzman.school_project.model.Role;
import com.rodrigoguzman.school_project.model.SecuredUser;
import com.rodrigoguzman.school_project.model.Student;
import com.rodrigoguzman.school_project.repository.IProfessorRepository;
import com.rodrigoguzman.school_project.repository.IRoleRepository;
import com.rodrigoguzman.school_project.repository.ISecuredUserRepository;
import com.rodrigoguzman.school_project.repository.IStudentRepository;
import com.rodrigoguzman.school_project.utils.RolesUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SchoolUserService implements ISchoolUserService {

    final IRoleRepository roleRepository;
    final ISecuredUserService userService;
    final ISecuredUserRepository userRepository;
    final IStudentRepository studentRepository;
    final IProfessorRepository professorRepository;

    @Override
    public SchoolUserResponseDTO createSchoolUser(SchoolUserRequestDTO schoolUser, String role) {
        Optional<Role> userRole = roleRepository.findByRole(role);
        Set<Role> roleSet = new HashSet<>();

        if (userRole.isPresent()) {
            roleSet.add(userRole.get());
        } else {
            roleSet.add(roleRepository.save(Role.builder().role(role).build()));
        }

        SecuredUserRequestDTO newSchoolUser = SecuredUserRequestDTO.builder()
                .username(schoolUser.username())
                .password(schoolUser.password())
                .roles(roleSet)
                .build();

        userService.createSecuredUser(newSchoolUser);

        Optional<SecuredUser> securedUser = userRepository
                .findByUsername(schoolUser.username());

        if (role.equals("Student")) {
            studentRepository.save(Student.builder()
                    .name(schoolUser.name())
                    .dni(schoolUser.dni())
                    .schoolUser(
                            securedUser.orElseThrow(() -> new RuntimeException("User not found")))
                    .courses(schoolUser.courses())
                    .build());
        } else if (role.equals("Professor")) {
            professorRepository.save(Professor.builder()
                    .name(schoolUser.name())
                    .dni(schoolUser.dni())
                    .schoolUser(
                            securedUser.orElseThrow(() -> new RuntimeException("User not found")))
                    .courses(schoolUser.courses())
                    .build());
        }

        return SchoolUserResponseDTO.builder()
                .username(schoolUser.username())
                .name(schoolUser.name())
                .dni(schoolUser.dni())
                .roles(RolesUtils.convertRolesToDTO(roleSet))
                .courses(schoolUser.courses())
                .build();
    }

    @Override
    public List<SchoolUserResponseDTO> findAllSchoolUsersByRole(String role) {
        if (role.equals("Student")) {
            return studentRepository.findAll().stream()
                    .map(student -> SchoolUserResponseDTO.builder()
                            .username(student.getSchoolUser().getUsername())
                            .name(student.getName())
                            .dni(student.getDni())
                            .roles(RolesUtils.convertRolesToDTO(student.getSchoolUser().getRolesList()))
                            .courses(student.getCourses())
                            .build())
                    .collect(Collectors.toList());
        } else if (role.equals("Professor")) {
            return professorRepository.findAll().stream()
                    .map(professor -> SchoolUserResponseDTO.builder()
                            .username(professor.getSchoolUser().getUsername())
                            .name(professor.getName())
                            .dni(professor.getDni())
                            .roles(RolesUtils.convertRolesToDTO(professor.getSchoolUser().getRolesList()))
                            .courses(professor.getCourses())
                            .build())
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public void deleteSchoolUser(Long id, String role) {
        if (role.equals("Student")) {
            Student studentToDelete = studentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student not found"));
            studentRepository.deleteById(id);
            userService.deleteSecuredUser(studentToDelete.getSchoolUser().getId());
        } else if (role.equals("Professor")) {
            Professor professorToDelete = professorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Professor not found"));
            professorRepository.deleteById(id);
            userService.deleteSecuredUser(professorToDelete.getSchoolUser().getId());
        }
    }

    @Override
    public List<SchoolUserResponseDTO> findAllSchoolUsers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<SchoolUserResponseDTO> findSchoolUserById(Long id, String role) {
        if (role.equals("Student")) {
            return studentRepository.findById(id).map(student -> SchoolUserResponseDTO.builder()
                    .username(student.getSchoolUser().getUsername())
                    .name(student.getName())
                    .dni(student.getDni())
                    .roles(RolesUtils.convertRolesToDTO(student.getSchoolUser().getRolesList()))
                    .courses(student.getCourses())
                    .build());
        } else if (role.equals("Professor")) {
            return professorRepository.findById(id).map(professor -> SchoolUserResponseDTO.builder()
                    .username(professor.getSchoolUser().getUsername())
                    .name(professor.getName())
                    .dni(professor.getDni())
                    .roles(RolesUtils.convertRolesToDTO(professor.getSchoolUser().getRolesList()))
                    .courses(professor.getCourses())
                    .build());
        }

        return Optional.empty();
    }

    @Override
    public SchoolUserResponseDTO updateSchoolUser(Long id, SchoolUserRequestDTO schoolUser, String role) {
        Set<Role> rolesList = new HashSet<>();

        for (Role r : schoolUser.roles()) {
            Role foundRole = roleRepository.findByRole(r.getRole())
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            rolesList.add(foundRole);
        }

        // TODO: Chequear que existan los cursos

        if (role.equals("Student")) {
            Student studentToEdit = studentRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            studentToEdit.setName(schoolUser.name() != null && !schoolUser.name().isEmpty()
                    ? schoolUser.name()
                    : studentToEdit.getName());
            studentToEdit.setDni(schoolUser.dni() != null && !schoolUser.dni().isEmpty()
                    ? schoolUser.dni()
                    : studentToEdit.getDni());
            studentToEdit.setCourses(schoolUser.courses());

            studentRepository.save(studentToEdit);

            return SchoolUserResponseDTO.builder()
                    .username(studentToEdit.getSchoolUser().getUsername())
                    .name(studentToEdit.getName())
                    .dni(studentToEdit.getDni())
                    .courses(studentToEdit.getCourses())
                    .roles(RolesUtils.convertRolesToDTO(rolesList))
                    .build();
        } else if (role.equals("Professor")) {
            Professor professorToEdit = professorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Professor not found"));

            professorToEdit.setName(schoolUser.name() != null && !schoolUser.name().isEmpty()
                    ? schoolUser.name()
                    : professorToEdit.getName());
            professorToEdit.setDni(schoolUser.dni() != null && !schoolUser.dni().isEmpty()
                    ? schoolUser.dni()
                    : professorToEdit.getDni());
            professorToEdit.setCourses(schoolUser.courses());

            professorRepository.save(professorToEdit);

            return SchoolUserResponseDTO.builder()
                    .username(professorToEdit.getSchoolUser().getUsername())
                    .name(professorToEdit.getName())
                    .dni(professorToEdit.getDni())
                    .courses(professorToEdit.getCourses())
                    .roles(RolesUtils.convertRolesToDTO(rolesList))
                    .build();
        }

        return null;
    }

}
