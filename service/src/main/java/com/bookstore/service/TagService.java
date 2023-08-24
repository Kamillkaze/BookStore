package com.bookstore.service;

import com.bookstore.dto.TagDto;
import com.bookstore.exception.TagCountBelowZeroException;
import com.bookstore.mapper.TagMapper;
import com.bookstore.model.Tag;
import com.bookstore.repository.TagRepository;
import com.mongodb.DuplicateKeyException;
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

    public TagDto addNewTag(String name) throws DuplicateKeyException {
        Tag tag = new Tag(name);

        return tagMapper.toDto(tagRepository.insert(tag));
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

    public void incrementTagCount(String name) {
        Tag tag = tagRepository.findByName(name)
                .orElseThrow(NoSuchElementException::new)
                .incrementCountByOne();

        tagRepository.save(tag);
    }

    public void decrementTagCount(String name) throws TagCountBelowZeroException {
        Tag tag = tagRepository.findByName(name)
                .orElseThrow(NoSuchElementException::new)
                .decrementCountByOne();

        tagRepository.save(tag);
    }
}
