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
        List<Book> all = bookRepository.findAll();

        return convertToBookDtoList(all);
    }

    public BookDto getBookById(String urlId) {
        Book byUrlId = bookRepository.findByUrlId(urlId)
                            .orElseThrow(NoSuchElementException::new);

        return BookMapper.toDto(byUrlId);
    }

    public List<BookDto> getAllBooksByTag(String tag) {
        List<Book> foundByTag = bookRepository.findByTagsContainingIgnoreCase(tag);

        return convertToBookDtoList(foundByTag);
    }

    public List<BookDto> getAllBooksByPhrase(String phrase) {
        List<Book> foundByPhrase = bookRepository.findByTitleContainingIgnoreCase(phrase);

        return convertToBookDtoList(foundByPhrase);
    }

    private List<BookDto> convertToBookDtoList(List<Book> books) {
        return books
                .stream()
                .map(BookMapper::toDto)
                .toList();
    }
}
