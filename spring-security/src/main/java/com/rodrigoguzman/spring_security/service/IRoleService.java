package com.rodrigoguzman.spring_security.service;

import java.util.List;
import java.util.Optional;

import com.rodrigoguzman.spring_security.model.Role;

public interface IRoleService {
    Role save(Role role);

    List<Role> findAll();

    Optional<Role> findById(Long id);

    Role update(Role role);

    void deleteById(Long id);

}
