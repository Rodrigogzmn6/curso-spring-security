package com.rodrigoguzman.school_project.utils;

import java.util.Set;
import java.util.stream.Collectors;

import com.rodrigoguzman.school_project.dto.RoleResponseDTO;
import com.rodrigoguzman.school_project.model.Role;

public class RolesUtils {
    public static Set<RoleResponseDTO> convertRolesToDTO(Set<Role> roles) {
        return roles.stream()
                .map(role -> RoleResponseDTO.builder()
                        .role(role.getRole())
                        .permissionsList(PermissionsUtils.convertPermissionsToDTO(role.getPermissionsList()))
                        .build())
                .collect(Collectors.toSet());
    }
}
