package com.library.app.genre.service;

import com.library.app.genre.exception.GenreException;
import com.library.app.genre.model.Genre;
import com.library.app.genre.repository.GenreRepository;
import com.library.app.genre.service.mapper.GenreMapper;
import com.library.app.genre.web.dto.GenreRequest;
import com.library.app.genre.web.dto.GenreResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.library.app.genre.service.mapper.GenreMapper.*;

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
        Genre genre = mapGenreRequest(genreRequest);

        save(genre);
        log.info("Genre created successfully: {}", genre);

        return getGenreResponse(genre);
    }


    public List<GenreResponse> getAllGenres() {
        return genreRepository.findAll().stream().map(GenreMapper::getGenreResponse).toList();
    }

    public GenreResponse getGenreById(String id) {
        Optional<Genre> genre = genreRepository.findById(UUID.fromString(id));

        return genre.map(GenreMapper::getGenreResponse).orElseThrow(() -> new GenreException("Genre not found"));
    }

    public GenreResponse updateGenre(GenreRequest genreRequest) {
        Genre genre = genreRepository.findGenreByCode(genreRequest.code()).orElseThrow(() -> new GenreException("Genre not found"));

        updateGenreFromEntity(genreRequest, genre);

        save(genre);

        return getGenreResponse(genre);
    }


}
