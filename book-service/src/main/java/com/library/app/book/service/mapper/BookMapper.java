package com.library.app.book.service.mapper;

import com.library.app.book.client.GenreClient;
import com.library.app.book.model.Book;
import com.library.app.book.web.dto.BookRequest;
import com.library.app.book.web.dto.BookResponse;
import com.library.app.book.web.dto.GenreResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class BookMapper {

    public Book getBook(BookRequest bookRequest, UUID genreId) {

        return Book.builder()
                .isbn(bookRequest.isbn())
                .title(bookRequest.title())
                .author(bookRequest.author())
                .genreId(genreId)
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

    public @NonNull BookResponse getBookResponse(Book book) {
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

    public void updateBook(BookRequest bookRequest, Book book, UUID genreId) {
        book.setIsbn(bookRequest.isbn());
        book.setTitle(bookRequest.title());
        book.setAuthor(bookRequest.author());
        book.setGenreId(genreId);
        book.setPublisher(bookRequest.publisher());
        book.setLanguage(bookRequest.language());
        book.setDescription(bookRequest.description());
        book.setPublicationDate(bookRequest.publicationDate());
        book.setPages(bookRequest.pages());
        book.setPrice(bookRequest.price());
        book.setCoverImageUrl(bookRequest.coverImageUrl());
    }
}
