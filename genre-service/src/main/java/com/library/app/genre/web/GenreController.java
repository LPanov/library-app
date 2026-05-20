package com.library.app.genre.web;

import com.library.app.genre.model.Genre;
import com.library.app.genre.service.GenreService;
import com.library.app.genre.web.dto.GenreRequest;
import com.library.app.genre.web.dto.GenreResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

        @PostMapping
        public ResponseEntity<GenreResponse> addGenre(@RequestBody @Valid GenreRequest genreRequest) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(genreService.createGenre(genreRequest));
        }

        @GetMapping
        public ResponseEntity<?> getAllGenres() {
            return ResponseEntity.ok(genreService.getAllGenres());
        }

        @PutMapping
        public ResponseEntity<GenreResponse> updateGenre(@RequestBody @Valid GenreRequest genreRequest) {
            return ResponseEntity.ok(genreService.updateGenre(genreRequest));
        }
    }
