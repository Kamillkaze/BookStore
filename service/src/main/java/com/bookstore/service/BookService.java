package com.bookstore.service;

import com.bookstore.dto.BookDto;
import com.bookstore.exception.CustomTimestampException;
import com.bookstore.mapper.BookMapper;
import com.bookstore.model.Book;
import com.bookstore.repository.BookRepository;
import jakarta.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final Clock clock;

    public BookService(BookRepository bookRepository, BookMapper bookMapper, Clock clock) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.clock = clock;
    }

    @Transactional
    public BookDto addBook(BookDto bookDto) {
        handleLastModified(bookDto);

        Book book = bookMapper.toEntity(bookDto);
        Book inserted = bookRepository.save(book);

        return bookMapper.toDto(inserted);
    }

    @Transactional
    public BookDto updateBook(BookDto bookDto) {
        handleLastModified(bookDto);
        Long id =
                bookRepository.getIdByUrlId(bookDto.getUrlId()).orElseThrow(NoSuchElementException::new);
        bookDto.setId(id);

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
        return bookRepository.findByUrlId(urlId).orElseThrow(NoSuchElementException::new);
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
        return books.stream().map(bookMapper::toDto).toList();
    }

    private void handleLastModified(BookDto bookDto) {
        if (Objects.nonNull(bookDto.getLastModified())) {
            throw new CustomTimestampException();
        }
        bookDto.setLastModified(LocalDateTime.now(clock));
    }
}
