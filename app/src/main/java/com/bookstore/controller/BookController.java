package com.bookstore.controller;

import com.bookstore.dto.BookDto;
import com.bookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "http://localhost:4200")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {

        List<BookDto> books = bookService.getAllBooks();

        return ResponseEntity.ok(books);
    }

    @GetMapping("/{urlId}")
    public ResponseEntity<BookDto> getBookById(@PathVariable(value = "urlId") String urlId) {

        BookDto book = bookService.getBookById(urlId);

        return ResponseEntity.ok(book);
    }

    @GetMapping("/tag/{tag}")
    public ResponseEntity<List<BookDto>> getAllBooksByTag(@PathVariable(value = "tag") String tag) {
        List<BookDto> allBooksByTag = bookService.getAllBooksByTag(tag);

        return ResponseEntity.ok(allBooksByTag);
    }

    @GetMapping("/phrase/{phrase}")
    public ResponseEntity<List<BookDto>> getAllBooksByPhrase(@PathVariable(value = "phrase") String phrase) {
        List<BookDto> allBooksByPhrase = bookService.getAllBooksByPhrase(phrase);

        return ResponseEntity.ok(allBooksByPhrase);
    }

    @PostMapping
    public ResponseEntity<BookDto> addBook(@RequestBody @Valid BookDto bookDto) {
        BookDto returned = bookService.addBook(bookDto);

        return new ResponseEntity<>(returned, HttpStatus.CREATED);
    }
}
