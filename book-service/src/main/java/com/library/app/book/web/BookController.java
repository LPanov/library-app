package com.library.app.book.web;

import com.library.app.book.exception.BookException;
import com.library.app.book.service.BookService;
import com.library.app.book.web.dto.*;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody @Valid BookRequest bookRequest) {
        try {
            BookResponse bookResponse = bookService.createBook(bookRequest);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(bookResponse);
        } catch (BookException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<List<BookResponse>> createBookBulk(@RequestBody @Valid List<BookRequest> bookRequests) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.createBookBulk(bookRequests));
    }

    @GetMapping
    public ResponseEntity<Page<BookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/available")
    public ResponseEntity<Page<BookResponse>> getAllAvailableBooks() {
        return ResponseEntity.ok(bookService.getAllAvailableBooks());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<BookResponse>> searchBooks(@NonNull SearchBookRequest searchBookRequest) {
        return ResponseEntity.ok(bookService.searchBooksWithFilters(searchBookRequest));
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookResponse> getBookByISBN(@PathVariable String isbn) {
        try {
            return ResponseEntity.ok(bookService.getBookByISBN(isbn));
        } catch (BookException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping
    public ResponseEntity<BookResponse> updateBook(@RequestBody @Valid BookRequest bookRequest) {
        try {
            BookResponse bookResponse = bookService.updateBook(bookRequest);
            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(bookResponse);
        } catch (BookException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable String isbn) {
        bookService.deleteBook(isbn);
        return ResponseEntity.ok(new ApiResponse("Book deleted successfully", true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> hardDeleteBook(@PathVariable UUID id) {
        bookService.hardDeleteBook(id);
        return ResponseEntity.ok(new ApiResponse("Book permanently deleted", true));
    }

    @GetMapping("/stats")
    public ResponseEntity<BookStatsResponse> getBookStats() {
        return ResponseEntity.ok(bookService.getBookStats());
    }
}
