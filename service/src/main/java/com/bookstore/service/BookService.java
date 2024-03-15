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
    private final BookMapper bookMapper;
    private final TagService tagService;

    public BookService(BookRepository bookRepository, BookMapper bookMapper, TagService tagService) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.tagService = tagService;
    }

    public BookDto addBook(BookDto bookDto) throws DuplicateKeyException {
        Book book = bookMapper.toDocument(bookDto);

        Book inserted = bookRepository.insert(book);
        incrementTagCountsIfExists(inserted.getTags());

        return bookMapper.toDto(inserted);
    }

    public BookDto deleteABook(String urlId) {
        Book deleted = bookRepository.deleteByUrlId(urlId)
                .orElseThrow(NoSuchElementException::new);
        decrementTagCountsIfExists(deleted.getTags());

        return bookMapper.toDto(deleted);
    }

    public List<BookDto> getAllBooks() {
        List<Book> all = bookRepository.findAll();

        return convertToBookDtoList(all);
    }

    public BookDto getBookById(String urlId) {
        Book byUrlId = bookRepository.findByUrlId(urlId)
                            .orElseThrow(NoSuchElementException::new);

        return bookMapper.toDto(byUrlId);
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
                .map(bookMapper::toDto)
                .toList();
    }

    private void incrementTagCountsIfExists(List<String> tags) {
        if (!tags.isEmpty()) {
            tags.forEach(tagService::incrementTagCount);
        }
    }

    private void decrementTagCountsIfExists(List<String> tags) {
        if (!tags.isEmpty()) {
            tags.forEach(tagService::decrementTagCount);
        }
    }
}
