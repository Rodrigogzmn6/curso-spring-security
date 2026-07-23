package com.rodrigoguzman.spring_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rodrigoguzman.spring_security.model.Role;

public interface IRoleRepository extends JpaRepository<Role, Long> {

}
