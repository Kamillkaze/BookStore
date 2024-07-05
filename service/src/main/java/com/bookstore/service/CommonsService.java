package com.bookstore.service;

import com.bookstore.dto.BookDto;
import com.bookstore.dto.TagDto;
import com.bookstore.mapper.BookMapper;
import com.bookstore.model.Book;
import com.bookstore.model.Tag;
import org.springframework.stereotype.Service;

@Service
public class CommonsService {

    private final BookService bookService;
    private final BookMapper bookMapper;
    private final TagService tagService;

    public CommonsService(BookService bookService, BookMapper bookMapper, TagService tagService) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
        this.tagService = tagService;
    }

    public TagDto addBookToTag(String name, String bookUrlId) {
        BookDto bookDto = bookService.getBookById(bookUrlId);
        Book toBeAdded = bookMapper.toEntity(bookDto);

        Tag tag = tagService.findTagByName(name);
        tag.addBook(toBeAdded);
        return tagService.updateTag(tag);
    }
}
