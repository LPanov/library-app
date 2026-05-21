package com.library.app.book.service;

import com.library.app.book.exception.BookException;
import com.library.app.book.model.Book;
import com.library.app.book.repository.BookRepository;
import com.library.app.book.service.mapper.BookMapper;
import com.library.app.book.web.dto.BookRequest;
import com.library.app.book.web.dto.BookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    public Page<BookResponse> getAllBooks(Pageable pageable) {
        Page<Book> bookPage = bookRepository.findAll(pageable);

        return bookPage.map(BookMapper :: getBookResponse);
    }

    public Page<BookResponse> getAllAvailableBooks(Pageable pageable) {
        Page<Book> bookPage = bookRepository.findBooksByActiveTrue(pageable);

        return bookPage.map(BookMapper :: getBookResponse);
    }

    public BookResponse createBook(BookRequest bookRequest) {
        Book book = BookMapper.getBook(bookRequest);

        saveBook(book);
        log.info("Book created {} successfully", book.getTitle());

        return BookMapper.getBookResponse(book);
    }

    public BookResponse getBookById(@NonNull UUID id) {
        Book book = getBook(id);

        return BookMapper.getBookResponse(book);
    }

    private @NonNull Book getBook(@NonNull UUID id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookException("Book not found with id: " + id));
    }

    public BookResponse getBookByIsbn(@NonNull String isbn) {
        Book book = bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookException("Book not found with isbn: " + isbn));

        return BookMapper.getBookResponse(book);
    }

    public BookResponse updateBook(@NonNull UUID id, BookRequest bookRequest) {
        Book book = getBook(id);

        BookMapper.updateBook(bookRequest, book);

        saveBook(book);
        log.info("Book updated {} successfully", book.getTitle());

        return BookMapper.getBookResponse(book);
    }

    private void saveBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(@NonNull UUID id) {
        Book book = getBook(id);
        bookRepository.delete(book);
    }


}
