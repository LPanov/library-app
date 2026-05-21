package com.library.app.book.web.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record BookResponse(
        UUID id,
        String isbn,
        String title,
        String author,
        UUID genreId,
        Integer availableCopies,
        BigDecimal price,
        String coverImageUrl
) {}

