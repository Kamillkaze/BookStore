package com.bookstore.service;

import com.bookstore.dto.BookDto;
import com.bookstore.mapper.BookMapper;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import com.mongodb.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDto addBook(BookDto bookDto) throws DuplicateKeyException {
        Book book = BookMapper.toDocument(bookDto);

        return BookMapper.toDto(bookRepository.insert(book));
    }

    public List<BookDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toDto)
                .toList();
    }

    public BookDto getBookById(String urlId) {
        Book byUrlId = bookRepository.findByUrlId(urlId)
                            .orElseThrow(NoSuchElementException::new);

        return BookMapper.toDto(byUrlId);
    }

    public List<BookDto> getAllBooksByTag(String tag) {
        return bookRepository.findAll()
                .stream()
                .filter(book -> book.getTags()
                                    .stream()
                                    .map(String::toLowerCase)
                                    .toList()
                                    .contains(tag.toLowerCase()))
                .map(BookMapper::toDto)
                .toList();
    }

    public List<BookDto> getAllBooksByPhrase(String phrase) {
        return bookRepository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase().contains(phrase.toLowerCase()))
                .map(BookMapper::toDto)
                .toList();
    }
}
