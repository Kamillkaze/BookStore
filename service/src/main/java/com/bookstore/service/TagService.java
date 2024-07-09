package com.bookstore.service;

import com.bookstore.dto.TagDto;
import com.bookstore.mapper.TagMapper;
import com.bookstore.model.Tag;
import com.bookstore.repository.TagRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public TagDto updateTag(Tag updated) {
        return tagMapper.toDto(tagRepository.save(updated));
    }

    @Transactional
    public void deleteATag(String tagName) {
        int rowsAffected = tagRepository.deleteByName(tagName);

        if (rowsAffected == 0) {
           throw new NoSuchElementException();
        }
    }

    public List<TagDto> getAllTags() {
        return tagRepository.getAllTags();
    }

    public Tag findTagByName(String name) {
        return tagRepository.findByName(name)
                .orElseThrow(NoSuchElementException::new);
    }
}
