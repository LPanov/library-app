package com.library.app.book.repository;

import com.library.app.book.model.Book;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByIsbn(@NonNull String isbn);

    Page<Book> findBooksByActiveTrue(Pageable pageable);
}
