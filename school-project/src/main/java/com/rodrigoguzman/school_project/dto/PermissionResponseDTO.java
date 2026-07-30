package com.rodrigoguzman.school_project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PermissionResponseDTO(@NotNull @NotBlank String permission) {

}
