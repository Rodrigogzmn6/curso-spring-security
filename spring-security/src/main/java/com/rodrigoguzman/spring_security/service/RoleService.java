package com.rodrigoguzman.spring_security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.rodrigoguzman.spring_security.model.Role;
import com.rodrigoguzman.spring_security.repository.IRoleRepository;

@Service
public class RoleService implements IRoleService {
    final IRoleRepository roleRepository;

    RoleService(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(Long id) {
        return roleRepository.findById(id);
    }

    @Override
    public Role update(Role role) {
        Role existingRole = findById(role.getId()).orElse(null);

        if (existingRole == null)
            return null;

        existingRole.setRole(role.getRole());
        existingRole.setPermissionsList(role.getPermissionsList());
        return roleRepository.save(existingRole);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }
}
