package com.library.app.book.service;

import com.library.app.book.client.GenreClient;
import com.library.app.book.exception.BookException;
import com.library.app.book.model.Book;
import com.library.app.book.repository.BookRepository;
import com.library.app.book.service.mapper.BookMapper;
import com.library.app.book.web.dto.BookRequest;
import com.library.app.book.web.dto.BookResponse;
import com.library.app.book.web.dto.GenreResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final GenreClient genreClient;
    private final BookMapper bookMapper;

    public Page<BookResponse> getAllBooks(Pageable pageable) {
        Page<Book> bookPage = bookRepository.findAll(pageable);

        return bookPage.map(bookMapper :: getBookResponse);
    }

    public Page<BookResponse> getAllAvailableBooks(Pageable pageable) {
        Page<Book> bookPage = bookRepository.findBooksByActiveTrue(pageable);

        return bookPage.map(bookMapper :: getBookResponse);
    }

    public BookResponse createBook(BookRequest bookRequest) {

        if (bookRepository.existsByIsbn(bookRequest.isbn())) {
            throw new BookException("Book with isbn: " + bookRequest.isbn() + " already exists");
        }

        GenreResponse genre = genreClient.getGenreByName(bookRequest.genreName());
        Book book = bookMapper.getBook(bookRequest, genre.id());

        saveBook(book);
        log.info("Book created {} successfully", book.getTitle());

        return bookMapper.getBookResponse(book);
    }

    public List<BookResponse> createBookBulk(List<BookRequest> bookRequests) {

        List<BookResponse> bookResponses = new ArrayList<>();

        bookRequests.forEach(bookRequest -> bookResponses.add(createBook(bookRequest)));

        return bookResponses;
    }

    public BookResponse getBookById(@NonNull UUID id) {
        Book book = getBook(id);

        return bookMapper.getBookResponse(book);
    }

    private @NonNull Book getBook(@NonNull UUID id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookException("Book not found with id: " + id));
    }

    public BookResponse getBookByIsbn(@NonNull String isbn) {
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookException("Book not found with isbn: " + isbn));

        return bookMapper.getBookResponse(book);
    }

    public BookResponse updateBook(BookRequest bookRequest) {
        GenreResponse genre = genreClient.getGenreByName(bookRequest.genreName());
        Book book = bookRepository.findByIsbn(bookRequest.isbn()).orElseThrow(() -> new BookException("Book not found with isbn: " + bookRequest.isbn()));

        bookMapper.updateBook(bookRequest, book, genre.id());

        saveBook(book);
        log.info("Book updated {} successfully", book.getTitle());

        return bookMapper.getBookResponse(book);
    }

    private void saveBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(@NonNull UUID id) {
        Book book = getBook(id);
        bookRepository.delete(book);
    }


}
