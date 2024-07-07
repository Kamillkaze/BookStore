package com.bookstore.service;

import com.bookstore.dto.TagDto;
import com.bookstore.model.Book;
import com.bookstore.model.Tag;
import org.springframework.stereotype.Service;

@Service
public class CommonsService {

    private final BookService bookService;
    private final TagService tagService;

    public CommonsService(BookService bookService, TagService tagService) {
        this.bookService = bookService;
        this.tagService = tagService;
    }

    public TagDto addBookToTag(String tagName, String bookUrlId) {
        Book toBeAdded = bookService.getBookEntityById(bookUrlId);

        Tag tag = tagService.findTagByName(tagName);
        tag.addBook(toBeAdded);
        return tagService.updateTag(tag);
    }

    public TagDto removeBookFromTag(String tagName, String bookUrlId) {
        Book toBeRemoved = bookService.getBookEntityById(bookUrlId);

        Tag tag = tagService.findTagByName(tagName);
        tag.removeBook(toBeRemoved);

        return tagService.updateTag(tag);
    }
}
