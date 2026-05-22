package com.library.app.book.web.dto;

import java.util.UUID;

public record GenreResponse (
        UUID id,
        String code,
        String name,
        String description,
        Boolean active,
        Integer displayOrder) {
}
