package com.library.app.book.repository;

import com.library.app.book.model.Book;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByIsbn(@NonNull String isbn);

    Boolean existsByIsbn(@NonNull String isbn);

    Page<Book> findBooksByActiveTrue(Pageable pageable);

    @Query("SELECT b FROM Book b WHERE " +
            "(:genreId IS NULL OR b.genreId = :genreId) AND " +
            "(:searchTerm IS NULL OR " +
            " LOWER(b.title) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            " LOWER(b.author) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            " b.isbn LIKE CONCAT('%', :searchTerm, '%'))")
    Page<Book> globalSearchWithGenre(
            Pageable pageable,
            @Param("searchTerm") String searchTerm,
            @Param("genreId") UUID genreId);

    Long countBooksByActiveTrue();

    Long countBooksByActiveTrueAndGenreId(UUID genreId);


}
