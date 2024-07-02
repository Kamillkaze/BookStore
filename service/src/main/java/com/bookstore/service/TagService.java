package com.bookstore.service;

import com.bookstore.dto.TagDto;
import com.bookstore.exception.TagCountBelowZeroException;
import com.bookstore.mapper.TagMapper;
import com.bookstore.model.Book;
import com.bookstore.model.Tag;
import com.bookstore.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public TagService(TagRepository tagRepository, TagMapper tagMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
    }

    public TagDto addNewTag(String name) {
        Tag tag = new Tag(name);

        return tagMapper.toDto(tagRepository.save(tag));
    }

    public TagDto deleteATag(String tagName) {
        Tag deleted = tagRepository.deleteByName(tagName)
                .orElseThrow(NoSuchElementException::new);
        return tagMapper.toDto(deleted);
    }

    public List<TagDto> getAll() {
        return tagRepository.findAll()
                .stream()
                .map(tagMapper::toDto)
                .toList();
    }

    public void addBookToTag(String name, Book toBeAdded) {
        Tag tag = tagRepository.findByName(name)
                .orElseThrow(NoSuchElementException::new)
                .addBook(toBeAdded);

        tagRepository.save(tag);
    }

    public void removeBookFromTag(String name, Book toBeRemoved) throws TagCountBelowZeroException {
        Tag tag = tagRepository.findByName(name)
                .orElseThrow(NoSuchElementException::new)
                .removeBook(toBeRemoved);

        tagRepository.save(tag);
    }

    public Tag findTagByName(String name) {
        return tagRepository.findByName(name)
                .orElseThrow(NoSuchElementException::new);
    }
}
