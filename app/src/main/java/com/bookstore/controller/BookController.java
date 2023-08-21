package com.bookstore.controller;

import com.bookstore.dto.BookDto;
import com.bookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAllBooks() {

        List<BookDto> books = bookService.getAllBooks();

        return ResponseEntity.ok(books);
    }

    @GetMapping("/books/byId")
    public ResponseEntity<BookDto> getBookById(@RequestParam(value = "urlId") String urlId) {

        BookDto book = bookService.getBookById(urlId);

        return ResponseEntity.ok(book);
    }

    @GetMapping("/books/byTag")
    public ResponseEntity<List<BookDto>> getAllBooksByTag(@RequestParam(value = "tag") String tag) {
        List<BookDto> allBooksByTag = bookService.getAllBooksByTag(tag);

        return ResponseEntity.ok(allBooksByTag);
    }

    @GetMapping("/books/byPhrase")
    public ResponseEntity<List<BookDto>> getAllBooksByPhrase(@RequestParam(value = "phrase") String phrase) {
        List<BookDto> allBooksByPhrase = bookService.getAllBooksByPhrase(phrase);

        return ResponseEntity.ok(allBooksByPhrase);
    }

    @PostMapping("/books")
    public ResponseEntity<BookDto> addBook(@RequestBody @Valid BookDto bookDto) {
        BookDto returned = bookService.addBook(bookDto);

        return new ResponseEntity<>(returned, HttpStatus.CREATED);
    }
}
