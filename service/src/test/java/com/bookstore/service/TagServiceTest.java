package com.bookstore.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bookstore.mapper.TagMapper;
import com.bookstore.model.Tag;
import com.bookstore.repository.TagRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @InjectMocks private TagService tagService;

    @Mock private TagRepository tagRepository;

    @Mock private TagMapper tagMapper;

    @Test
    @DisplayName("Should add new tag properly")
    void addNewTag() {
        String tagName = "tag1";
        Tag toBeSaved = new Tag(tagName);
        Tag saved = new Tag(tagName);
        saved.setId(1L);
        when(tagRepository.save(toBeSaved)).thenReturn(saved);

        tagService.addNewTag(tagName);

        verify(tagRepository).save(toBeSaved);
        verify(tagMapper).toDto(saved);
    }

    @Test
    @DisplayName("Should delete a tag correctly")
    void deleteATag() {
        String tagName = "tag1";
        when(tagRepository.deleteByName(tagName)).thenReturn(1);

        assertThatCode(() -> tagService.deleteATag(tagName)).doesNotThrowAnyException();

        verify(tagRepository).deleteByName(tagName);
    }

    @Test
    @DisplayName("Should throw NoSuchElementException while deleting a Tag which not exist")
    void deleteATagNotFound() {
        String tagName = "tag1";
        when(tagRepository.deleteByName(tagName)).thenReturn(0);

        assertThatThrownBy(() -> tagService.deleteATag(tagName))
                .isInstanceOf(NoSuchElementException.class);

        verify(tagRepository).deleteByName(tagName);
    }

    @Test
    @DisplayName("Should call getAllTags() from tagRepository")
    void getAllTags() {
        tagService.getAllTags();

        verify(tagRepository).getAllTags();
    }

    @Test
    @DisplayName("Should throw an exception if searched tag does not exist")
    void findTagByNameWhenNotFound() {
        String tagName = "tag1";
        when(tagRepository.findByName(tagName)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tagService.findTagByName(tagName))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Should return a tag correctly if it exists")
    void findTagByName() {
        String tagName = "tag1";
        Tag tag = new Tag(tagName);
        when(tagRepository.findByName(tagName)).thenReturn(Optional.of(tag));

        Tag result = tagService.findTagByName(tagName);

        assertThat(result).isEqualTo(tag);
    }
}
