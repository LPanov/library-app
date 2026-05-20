package com.library.app.genre.service.mapper;

import com.library.app.genre.model.Genre;
import com.library.app.genre.web.dto.GenreRequest;
import com.library.app.genre.web.dto.GenreResponse;
import org.jspecify.annotations.NonNull;

public class GenreMapper {
    public static Genre mapGenreRequest(GenreRequest genreRequest) {
        return Genre.builder()
                .code(genreRequest.code())
                .name(genreRequest.name())
                .description(genreRequest.description())
                .displayOrder(genreRequest.displayOrder())
                .build();
    }

    public static @NonNull GenreResponse getGenreResponse(Genre genre) {
        return new GenreResponse(genre.getId(), genre.getCode(), genre.getName(), genre.getDescription(), genre.getActive(), genre.getDisplayOrder());
    }

    public static void updateGenreFromEntity(GenreRequest genreRequest, Genre genre) {
        if (genre != null && genreRequest != null) {
            genre.setName(genreRequest.name());
            genre.setDescription(genreRequest.description());
            genre.setCode(genreRequest.code());
            genre.setDisplayOrder(genreRequest.displayOrder() != null ? genreRequest.displayOrder() : 0);
        }

    }
}
