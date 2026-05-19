package com.library.app.genre.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

public record GenreRequest(
        @NotBlank(message = "Code cannot be empty")
        String code,
        @NotBlank(message = "Name cannot be empty")
        String name,
        @Length(max = 500, message = "Description cannot be more than 500 characters")
        String description,
        @Min(value = 0, message = "Display order cannot be negative")
        Integer displayOrder) {
}
