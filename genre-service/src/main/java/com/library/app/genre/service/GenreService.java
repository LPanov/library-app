package com.library.app.genre.service;

import com.library.app.genre.model.Genre;
import com.library.app.genre.repository.GenreRepository;
import com.library.app.genre.service.mapper.GenreMapper;
import com.library.app.genre.web.dto.GenreRequest;
import com.library.app.genre.web.dto.GenreResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        Genre genre = GenreMapper.mapGenreRequest(genreRequest);

        save(genre);
        log.info("Genre created successfully: {}", genre);

        return GenreMapper.getGenreResponse(genre);
    }


    public List<GenreResponse> getAllGenres() {
        return genreRepository.findAll().stream().map(GenreMapper::getGenreResponse).toList();
    }

    public GenreResponse getGenreById(String id) {
        Optional<Genre> genre = genreRepository.findById(UUID.fromString(id));

        return genre.map(GenreMapper::getGenreResponse).orElse(null);
    }
}
