package com.rodrigoguzman.spring_security.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonPropertyOrder({ "username", "message", "jwt", "status" })
public record AuthResponseDTO(
        @NotBlank @NotNull String username,
        @NotBlank @NotNull String message,
        @NotBlank @NotNull String jwt,
        @NotBlank @NotNull boolean status) {
}