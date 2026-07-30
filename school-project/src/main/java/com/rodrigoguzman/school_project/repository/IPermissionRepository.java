package com.rodrigoguzman.school_project.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigoguzman.school_project.model.Permission;

public interface IPermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByPermission(String permission);
}
