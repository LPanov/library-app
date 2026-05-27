package com.library.app.book.web.dto;

public record SearchBookRequest(String genreName, String searchTerm, Integer page, Integer size, String sortBy, String sortDirection) {
}
