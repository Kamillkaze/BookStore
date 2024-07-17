package com.bookstore.service;

import com.bookstore.dto.BookDto;
import com.bookstore.mapper.BookMapper;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional
    public BookDto addBook(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        Book inserted = bookRepository.save(book);

        return bookMapper.toDto(inserted);
    }

    @Transactional
    public void deleteABook(String urlId) {
        int rowsAffected = bookRepository.deleteByUrlId(urlId);

        if (rowsAffected == 0) {
            throw new NoSuchElementException();
        }
    }

    public List<BookDto> getAllBooks() {
        List<Book> all = bookRepository.findAllBooks();

        return convertToBookDtoList(all);
    }

    public Book getBookEntityById(String urlId) {
        return bookRepository.findByUrlId(urlId)
                            .orElseThrow(NoSuchElementException::new);
    }

    public BookDto getBookById(String urlId) {
        return bookMapper.toDto(getBookEntityById(urlId));
    }

    public List<BookDto> getAllBooksByTag(String tagName) {
        List<Book> foundByTag = bookRepository.findByTagName(tagName);

        return convertToBookDtoList(foundByTag);
    }

    public List<BookDto> getAllBooksByPhrase(String phrase) {
        List<Book> foundByPhrase = bookRepository.findByUrlIdContainingPhrase(phrase);

        return convertToBookDtoList(foundByPhrase);
    }

    private List<BookDto> convertToBookDtoList(List<Book> books) {
        return books
                .stream()
                .map(bookMapper::toDto)
                .toList();
    }
}
