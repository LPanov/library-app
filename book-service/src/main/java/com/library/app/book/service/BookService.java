package com.library.app.book.service;

import com.library.app.book.model.Book;
import com.library.app.book.repository.BookRepository;
import com.library.app.book.service.mapper.BookMapper;
import com.library.app.book.web.dto.BookRequest;
import com.library.app.book.web.dto.BookResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    public BookResponse createBook(BookRequest bookRequest) {
        Book book = BookMapper.getBook(bookRequest);

        bookRepository.save(book);
        log.info("Book created {} successfully", book.getTitle());

        return BookMapper.getBookResponse(book);
    }




}
