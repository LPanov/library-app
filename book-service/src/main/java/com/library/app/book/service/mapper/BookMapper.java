package com.library.app.book.service.mapper;

import com.library.app.book.model.Book;
import com.library.app.book.web.dto.BookRequest;
import com.library.app.book.web.dto.BookResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

public class BookMapper {
    public static Book getBook(BookRequest bookRequest) {
        return Book.builder()
                .isbn(bookRequest.isbn())
                .title(bookRequest.title())
                .author(bookRequest.author())
                .genreId(bookRequest.genreId())
                .publisher(bookRequest.publisher())
                .language(bookRequest.language())
                .description(bookRequest.description())
                .publicationDate(bookRequest.publicationDate())
                .pages(bookRequest.pages())
                .price(bookRequest.price())
                .copies(bookRequest.copies())
                .availableCopies(bookRequest.copies())
                .coverImageUrl(bookRequest.coverImageUrl())
                .build();
    }

    public static @NonNull BookResponse getBookResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getGenreId(),
                book.getAvailableCopies(),
                book.getPrice(),
                book.getCoverImageUrl());
    }
}
