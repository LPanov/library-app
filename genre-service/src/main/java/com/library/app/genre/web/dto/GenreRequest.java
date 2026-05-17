package com.library.app.genre.web.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public record GenreRequest(
        @NotBlank(message = "Code cannot be empty")
        String code,
        @NotBlank(message = "Name cannot be empty")
        String name,
        String description) {
}
