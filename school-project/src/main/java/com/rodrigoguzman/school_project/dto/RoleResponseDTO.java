package com.rodrigoguzman.school_project.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RoleResponseDTO(
        @NotNull @NotBlank String role,
        @NotNull @NotBlank Set<PermissionResponseDTO> permissionsList) {

}
