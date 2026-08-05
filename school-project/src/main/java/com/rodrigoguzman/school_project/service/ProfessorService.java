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
import com.rodrigoguzman.school_project.repository.IProfessorRepository;
import com.rodrigoguzman.school_project.repository.IRoleRepository;
import com.rodrigoguzman.school_project.repository.ISecuredUserRepository;
import com.rodrigoguzman.school_project.utils.RolesUtils;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfessorService implements IProfessorService {
    final IProfessorRepository repository;
    final ISecuredUserService userService;
    final IRoleRepository roleRepository;
    final ISecuredUserRepository userRepository;

    @Override
    @Transactional
    public SchoolUserResponseDTO createProfessor(SchoolUserRequestDTO professor) {
        Set<Role> professorRole = Set
                .of(roleRepository.findByRole("Professor")
                        .orElse(roleRepository.save(Role.builder().role("Professor").build())));

        SecuredUserRequestDTO professorUser = SecuredUserRequestDTO.builder()
                .username(professor.username())
                .password(professor.password())
                .roles(professorRole)
                .build();

        userService.createSecuredUser(professorUser);

        Optional<SecuredUser> securedUser = userRepository
                .findByUsername(professorUser.username());

        repository.save(Professor.builder()
                .name(professor.name())
                .dni(professor.dni())
                .schoolUser(
                        securedUser.orElseThrow(() -> new RuntimeException("User not found")))
                .courses(professor.courses())
                .build());

        return SchoolUserResponseDTO.builder()
                .username(professor.username())
                .name(professor.name())
                .dni(professor.dni())
                .roles(RolesUtils.convertRolesToDTO(professorRole))
                .courses(professor.courses())
                .build();
    }

    @Override
    public List<SchoolUserResponseDTO> findAllProfessors() {
        return repository.findAll().stream()
                .map(professor -> SchoolUserResponseDTO.builder()
                        .username(professor.getSchoolUser().getUsername())
                        .name(professor.getName())
                        .dni(professor.getDni())
                        .courses(professor.getCourses())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SchoolUserResponseDTO> findProfessorById(Long id) {
        Optional<Professor> foundProfessor = repository.findById(id);

        if (foundProfessor.isPresent()) {
            return Optional.of(SchoolUserResponseDTO.builder()
                    .username(foundProfessor.get().getSchoolUser().getUsername())
                    .name(foundProfessor.get().getName())
                    .dni(foundProfessor.get().getDni())
                    .courses(foundProfessor.get().getCourses())
                    .build());
        }

        return Optional.empty();
    }

    @Override
    public SchoolUserResponseDTO updateProfessor(Long id, SchoolUserRequestDTO professor) {
        Set<Role> rolesList = new HashSet<>();

        for (Role r : professor.roles()) {
            Role foundRole = roleRepository.findByRole(r.getRole())
                    .orElseThrow(() -> new RuntimeException("Role not found"));

            rolesList.add(foundRole);
        }

        // TODO: Chequear que existan los cursos

        Professor professorToEdit = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        professorToEdit.setName(professor.name() != null && !professor.name().isEmpty()
                ? professor.name()
                : professorToEdit.getName());
        professorToEdit.setDni(professor.dni() != null && !professor.dni().isEmpty()
                ? professor.dni()
                : professorToEdit.getDni());
        professorToEdit.setCourses(professor.courses());

        repository.save(professorToEdit);

        return SchoolUserResponseDTO.builder()
                .username(professorToEdit.getSchoolUser().getUsername())
                .name(professorToEdit.getName())
                .dni(professorToEdit.getDni())
                .courses(professorToEdit.getCourses())
                .roles(RolesUtils.convertRolesToDTO(rolesList))
                .build();
    }

    @Override
    public void deleteProfessor(Long id) {
        Professor professorToDelete = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Professor not found"));

        if (professorToDelete != null) {
            repository.deleteById(id);
            userService.deleteSecuredUser(professorToDelete.getSchoolUser().getId());
        }
    }
}
