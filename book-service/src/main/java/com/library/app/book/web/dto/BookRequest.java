package com.library.app.book.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookRequest(
        @Size(min = 20, max = 20, message = "ISBN must be 20 characters long")
        @NotBlank(message = "ISBN is required")
        String isbn,

        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Author is required")
        String author,

        @NotBlank(message = "Genre is required")
        String genreName,

        @Size(max = 100, message = "Publisher must be less than 100 characters")
        String publisher,

        @Size(max = 20, message = "Language must be less than 20 characters")
        String language,

        LocalDate publicationDate,

        @Min(value = 100, message = "Pages must be at least 100")
        @Max(value = 50000, message = "Pages cannot exceed 50000")
        Integer pages,

        @Size(max = 2000, message = "Description must be less than 2000 characters")
        String description,

        @Min(1)
        Integer copies,

        Integer availableCopies,
        BigDecimal price,

        @Size(max = 500, message = "Cover image URL must be less than 500 characters")
        String coverImageUrl
) {}
