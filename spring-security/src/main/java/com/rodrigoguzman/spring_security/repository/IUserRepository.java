package com.rodrigoguzman.spring_security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rodrigoguzman.spring_security.model.UserSec;

public interface IUserRepository extends JpaRepository<UserSec, Long> {
    Optional<UserSec> findByUsername(String username);
}
