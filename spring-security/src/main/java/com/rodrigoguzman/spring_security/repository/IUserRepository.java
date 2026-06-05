package com.rodrigoguzman.spring_security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rodrigoguzman.spring_security.model.UserSec;

@Repository
public interface IUserRepository extends JpaRepository<UserSec, Long> {
    Optional<UserSec> findByUsername(String username);
}
