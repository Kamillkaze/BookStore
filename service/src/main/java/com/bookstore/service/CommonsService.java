package com.bookstore.service;

import com.bookstore.dto.TagDto;
import com.bookstore.mapper.TagMapper;
import com.bookstore.model.Book;
import com.bookstore.model.Tag;
import com.bookstore.repository.CommonsRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CommonsService {

    private final BookService bookService;
    private final TagService tagService;
    private final TagMapper tagMapper;
    private final CommonsRepository commonsRepository;

    public CommonsService(BookService bookService, TagService tagService, TagMapper tagMapper, CommonsRepository commonsRepository) {
        this.bookService = bookService;
        this.tagService = tagService;
        this.tagMapper = tagMapper;
        this.commonsRepository = commonsRepository;
    }

    @Transactional
    public TagDto addBookToTag(String tagName, String bookUrlId) {
        Book toBeAdded = bookService.getBookEntityById(bookUrlId);
        Tag tag = tagService.findTagByName(tagName);

        commonsRepository.addBookToTag(tag.getId(), toBeAdded.getId());

        return tagMapper.toDto(tag);
    }

    @Transactional
    public TagDto removeBookFromTag(String tagName, String bookUrlId) {
        Book toBeRemoved = bookService.getBookEntityById(bookUrlId);
        Tag tag = tagService.findTagByName(tagName);

        commonsRepository.removeBookFromTag(tag.getId(), toBeRemoved.getId());

        return tagMapper.toDto(tag);
    }
}
