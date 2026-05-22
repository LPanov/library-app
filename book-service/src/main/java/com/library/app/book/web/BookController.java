package com.library.app.book.web;

import com.library.app.book.service.BookService;
import com.library.app.book.web.dto.BookRequest;
import com.library.app.book.web.dto.BookResponse;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody @Valid BookRequest bookRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(bookService.createBook(bookRequest));
    }
}
