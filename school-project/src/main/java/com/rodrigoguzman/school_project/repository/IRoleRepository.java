package com.rodrigoguzman.school_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigoguzman.school_project.model.Role;

public interface IRoleRepository extends JpaRepository<Role, Long> {

}
