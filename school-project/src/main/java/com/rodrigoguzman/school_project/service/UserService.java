package com.rodrigoguzman.school_project.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rodrigoguzman.school_project.dto.SecuredUserRequestDTO;
import com.rodrigoguzman.school_project.dto.SecuredUserResponseDTO;
import com.rodrigoguzman.school_project.model.Role;
import com.rodrigoguzman.school_project.model.SecuredUser;
import com.rodrigoguzman.school_project.repository.IRoleRepository;
import com.rodrigoguzman.school_project.repository.ISecuredUserRepository;
import com.rodrigoguzman.school_project.utils.RolesUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements ISecuredUserService {
    private final ISecuredUserRepository repository;
    private final IRoleRepository roleRepository;

    @Override
    public SecuredUserResponseDTO createSecuredUser(SecuredUserRequestDTO user) {
        if (user.roles().isEmpty())
            throw new RuntimeException("Error: Roles not found");

        Set<Role> rolesList = new HashSet<Role>();
        Role readRole;

        for (Role role : user.roles()) {
            readRole = roleRepository.findByRole(role.getRole()).orElse(null);

            if (readRole != null) {
                rolesList.add(readRole);
            }
        }

        // Encrypt password
        String encryptedPassword = this.encryptPassword(user.password());

        repository.save(
                SecuredUser.builder()
                        .username(user.username())
                        .password(encryptedPassword)
                        .enabled(true)
                        .accountNonExpired(true)
                        .accountNonLocked(true)
                        .credentialsNonExpired(true)
                        .rolesList(rolesList)
                        .build());

        return SecuredUserResponseDTO.builder()
                .username(user.username())
                .roles(RolesUtils.convertRolesToDTO(rolesList))
                .build();
    }

    @Override
    public List<SecuredUserResponseDTO> findAllSecuredUsers() {
        return repository.findAll().stream()
                .map(user -> SecuredUserResponseDTO.builder()
                        .username(user.getUsername())
                        .roles(RolesUtils.convertRolesToDTO(user.getRolesList()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<SecuredUserResponseDTO> findSecuredUserById(Long id) {
        SecuredUser user = repository.findById(id).orElse(null);

        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(SecuredUserResponseDTO.builder()
                .username(user.getUsername())
                .roles(RolesUtils.convertRolesToDTO(user.getRolesList()))
                .build());
    }

    @Override
    public Optional<SecuredUserResponseDTO> findSecuredUserByUsername(String username) {
        SecuredUser user = repository.findByUsername(username).orElse(null);

        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(SecuredUserResponseDTO.builder()
                .username(user.getUsername())
                .roles(RolesUtils.convertRolesToDTO(user.getRolesList()))
                .build());
    }

    @Override
    public SecuredUserResponseDTO updateSecuredUser(Long id, SecuredUserRequestDTO user) {
        SecuredUser userToEdit = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        userToEdit.setUsername(user.username() != null &&
                !user.username().isEmpty()
                        ? user.username()
                        : userToEdit.getUsername());

        userToEdit.setPassword(user.password() != null &&
                !user.password().isEmpty()
                        ? user.password()
                        : userToEdit.getPassword());

        Set<Role> newRolesList = new HashSet<Role>();

        for (Role role : user.roles()) {
            Role readRole = roleRepository.findByRole(role.getRole()).orElse(null);
            if (readRole != null) {
                newRolesList.add(readRole);
            }
        }

        userToEdit.setRolesList(newRolesList);

        repository.save(userToEdit);

        return SecuredUserResponseDTO.builder()
                .username(userToEdit.getUsername())
                .roles(RolesUtils.convertRolesToDTO(userToEdit.getRolesList()))
                .build();
    }

    @Override
    public void deleteSecuredUser(Long id) {
        repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        repository.deleteById(id);
    }

    @Override
    public String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
