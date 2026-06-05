package com.rodrigoguzman.spring_security.service;

import java.util.List;
import java.util.Optional;

import com.rodrigoguzman.spring_security.model.Permission;

public interface IPermissionService {
    Permission save(Permission permission);

    List<Permission> findAll();

    Optional<Permission> findById(Long id);

    Permission update(Permission permission);

    void deleteById(Long id);
}
