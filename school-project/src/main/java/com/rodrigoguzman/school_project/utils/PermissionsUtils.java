package com.rodrigoguzman.school_project.utils;

import java.util.Set;
import java.util.stream.Collectors;

import com.rodrigoguzman.school_project.dto.PermissionResponseDTO;
import com.rodrigoguzman.school_project.model.Permission;

public class PermissionsUtils {
    public static Set<PermissionResponseDTO> convertPermissionsToDTO(Set<Permission> permissions) {
        return permissions.stream()
                .map(permission -> PermissionResponseDTO.builder()
                        .permission(permission.getPermission())
                        .build())
                .collect(Collectors.toSet());
    }
}
