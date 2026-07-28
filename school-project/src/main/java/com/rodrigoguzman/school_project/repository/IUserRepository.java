package com.rodrigoguzman.school_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigoguzman.school_project.model.SchoolUser;

public interface IUserRepository extends JpaRepository<SchoolUser, Long> {
    Optional<SchoolUser> findSchoolUserEntityByUsername(String username);
}
