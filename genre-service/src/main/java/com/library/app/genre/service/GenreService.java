package com.library.app.genre.service;

import com.library.app.genre.model.Genre;
import com.library.app.genre.repository.GenreRepository;
import com.library.app.genre.web.dto.GenreRequest;
import com.library.app.genre.web.dto.GenreResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    private void save(Genre genre) {
        genreRepository.save(genre);
    }

    public GenreResponse createGenre(GenreRequest genreRequest) {
        Genre genre = Genre.builder()
                .code(genreRequest.code())
                .name(genreRequest.name())
                .build();

        save(genre);
        log.info("Genre created successfully: {}", genre);

        return new GenreResponse(genre.getId(), genre.getCode(), genre.getName(), genre.getDescription(), genre.getActive());
    }
}
