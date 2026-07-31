package com.rodrigoguzman.school_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigoguzman.school_project.model.SecuredUser;

public interface ISecuredUserRepository extends JpaRepository<SecuredUser, Long> {
    Optional<SecuredUser> findByUsername(String username);
}
