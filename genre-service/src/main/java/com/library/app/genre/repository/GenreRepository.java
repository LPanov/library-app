package com.library.app.genre.repository;

import com.library.app.genre.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface GenreRepository extends JpaRepository<Genre, UUID> {

    Optional<Genre> findGenreByCode(String code);

    Optional<Genre> findGenreByName(String name);
}
