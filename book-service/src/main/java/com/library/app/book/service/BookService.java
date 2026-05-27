package com.library.app.book.service;

import com.library.app.book.client.GenreClient;
import com.library.app.book.exception.BookException;
import com.library.app.book.model.Book;
import com.library.app.book.repository.BookRepository;
import com.library.app.book.service.mapper.BookMapper;
import com.library.app.book.web.dto.BookRequest;
import com.library.app.book.web.dto.BookResponse;
import com.library.app.book.web.dto.GenreResponse;
import com.library.app.book.web.dto.SearchBookRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;

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

    public Page<BookResponse> getAllBooks() {
        Pageable pageable = createPageable(100, 10, "id", "asc");

        Page<Book> bookPage = bookRepository.findAll(pageable);

        return bookPage.map(bookMapper :: getBookResponse);
    }

    public Page<BookResponse> getAllAvailableBooks() {
        Pageable pageable = createPageable(100, 10, "id", "asc");

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

    public Book getBookByIsbn(@NonNull String isbn) {
        return bookRepository.findByIsbn(isbn).orElseThrow(() -> new BookException("Book not found with isbn: " + isbn));
    }

    public BookResponse updateBook(BookRequest bookRequest) {
        GenreResponse genre = genreClient.getGenreByName(bookRequest.genreName());
        Book book = getBookByIsbn(bookRequest.isbn());

        bookMapper.updateBook(bookRequest, book, genre.id());

        saveBook(book);
        log.info("Book updated {} successfully", book.getTitle());

        return bookMapper.getBookResponse(book);
    }

    private void saveBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(String isbn) {
        Book book = getBookByIsbn(isbn);
        book.setActive(false);

        saveBook(book);
    }

    public void hardDeleteBook(@NonNull UUID id) {
        Book book = getBook(id);

        bookRepository.delete(book);
    }

    public Page<BookResponse> searchBooksWithFilters(SearchBookRequest searchBookRequest) {
        GenreResponse genre = searchBookRequest.genreName() != null ? genreClient.getGenreByName(searchBookRequest.genreName()) : null;
        Pageable pageable = createPageable(searchBookRequest.page(), searchBookRequest.size(), searchBookRequest.sortBy(), searchBookRequest.sortDirection());

        Page<Book> bookPage = bookRepository.globalSearchWithGenre(pageable, searchBookRequest.searchTerm(), genre != null ? genre.id() : null);

        return bookPage.map(bookMapper::getBookResponse);

    }

    private Pageable createPageable(int page, int size, String sortBy, String sortDirection) {
        size=Math.min(size, 10);
        size=Math.max(size, 1);

        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "id";
        }
        Sort sort = "asc".equalsIgnoreCase(sortDirection)
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        return PageRequest.of(page, size, sort);
    }


}
