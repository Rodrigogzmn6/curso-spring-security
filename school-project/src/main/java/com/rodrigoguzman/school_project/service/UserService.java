package com.rodrigoguzman.school_project.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rodrigoguzman.school_project.model.Role;
import com.rodrigoguzman.school_project.model.SchoolUser;
import com.rodrigoguzman.school_project.repository.IUserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository repository;

    @Override
    public SchoolUser createUser(SchoolUser user) {
        return repository.save(user);
    }

    @Override
    public List<SchoolUser> findAllUsers() {
        return repository.findAll();
    }

    @Override
    public Optional<SchoolUser> findUserById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<SchoolUser> findUserByUsername(String username) {
        return repository.findAll()
                .stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    @Override
    public String encryptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }

    @Override
    public SchoolUser updateUser(Long id, SchoolUser user) {
        SchoolUser userToEdit = findUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newUsername = user.getUsername() != null && !user.getUsername().isEmpty()
                ? user.getUsername()
                : userToEdit.getUsername();
        String newPassword = user.getPassword() != null && !user.getPassword().isEmpty()
                ? user.getPassword()
                : userToEdit.getPassword();
        Set<Role> newRolesList = user.getRolesList() != null
                ? user.getRolesList()
                : userToEdit.getRolesList();

        SchoolUser updatedUser = SchoolUser.builder()
                .id(userToEdit.getId())
                .username(newUsername)
                .password(newPassword)
                .enabled(user.isEnabled())
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .rolesList(newRolesList)
                .build();

        repository.save(updatedUser);
        return updatedUser;
    }

    @Override
    public void deleteUser(Long id) {
        SchoolUser userToEdit = findUserById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        repository.deleteById(userToEdit.getId());
    }
}
