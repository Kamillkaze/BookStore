package com.bookstore.service;

import com.bookstore.dto.BookDto;
import com.bookstore.mapper.BookMapper;
import com.bookstore.model.Book;
import com.bookstore.model.Tag;
import com.bookstore.repository.BookRepository;
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

    public BookDto addBook(BookDto bookDto) {
        Book book = bookMapper.toDocument(bookDto);

        Book inserted = bookRepository.save(book);
        inserted.getTags()
            .forEach(tag -> tagService.addBookToTag(tag.getName(), inserted));

        return bookMapper.toDto(inserted);
    }

    public BookDto deleteABook(String urlId) {
        Book deleted = bookRepository.deleteByUrlId(urlId)
                .orElseThrow(NoSuchElementException::new);
        deleted.getTags()
                .forEach(tag -> tagService.removeBookFromTag(tag.getName(), deleted));

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

    public List<BookDto> getAllBooksByTag(String tagName) {
        Tag tag = tagService.findTagByName(tagName);
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
}
