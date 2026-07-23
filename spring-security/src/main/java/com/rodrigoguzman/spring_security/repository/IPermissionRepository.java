package com.rodrigoguzman.spring_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rodrigoguzman.spring_security.model.Permission;

public interface IPermissionRepository extends JpaRepository<Permission, Long> {

}
