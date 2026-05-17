package com.library.app.genre.web.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record GenreResponse(
        UUID id,
        String code,
        String name,
        String description,
        Boolean active) {
}
