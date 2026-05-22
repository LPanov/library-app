package com.library.app.book.client;

import com.library.app.book.web.dto.GenreResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.UUID;

@HttpExchange("/api/v1/genres")
public interface GenreClient {

    @GetExchange("/{name}")
    GenreResponse getGenreByName(@PathVariable String name);
}
