package com.rodrigoguzman.spring_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rodrigoguzman.spring_security.model.Permission;

@Repository
public interface IPermissionRepository extends JpaRepository<Permission, Long> {

}
