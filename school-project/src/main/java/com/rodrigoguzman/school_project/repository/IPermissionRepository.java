package com.rodrigoguzman.school_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigoguzman.school_project.model.Permission;

public interface IPermissionRepository extends JpaRepository<Permission, Long> {

}
