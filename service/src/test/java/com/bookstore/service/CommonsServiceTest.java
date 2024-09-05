package com.bookstore.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.bookstore.dto.TagDto;
import com.bookstore.mapper.TagMapper;
import com.bookstore.model.Book;
import com.bookstore.model.Tag;
import com.bookstore.repository.CommonsRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommonsServiceTest {

    @InjectMocks private CommonsService commonsService;

    @Mock private BookService bookService;

    @Mock private TagService tagService;

    @Mock private TagMapper tagMapper;

    @Mock private CommonsRepository commonsRepository;

    @Test
    @DisplayName("Should throw NoSuchElementException if Tag not found addBookToTag()")
    void addBookToTagWhenTagNotFound() {
        String tagName = "tagName";
        String urlId = "url-id";
        when(bookService.getBookEntityById(urlId)).thenReturn(new Book());
        when(tagService.findTagByName(tagName)).thenThrow(new NoSuchElementException());

        assertThatThrownBy(() -> commonsService.addBookToTag(tagName, urlId))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Should throw NoSuchElementException if Book not found addBookToTag()")
    void addBookToTagWhenBookNotFound() {
        String tagName = "tagName";
        String urlId = "url-id";
        when(bookService.getBookEntityById(urlId)).thenThrow(new NoSuchElementException());

        assertThatThrownBy(() -> commonsService.addBookToTag(tagName, urlId))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Should add book to tag correctly addBookToTag()")
    void addBookToTagWhenEverythingOk() {
        String tagName = "tagName";
        String urlId = "author-title";
        Book book = new Book(urlId, "title", "author");
        book.setId(101L);
        Tag tag = new Tag(tagName);
        tag.setId(107L);
        TagDto expected = new TagDto(tagName, 1L);
        when(bookService.getBookEntityById(urlId)).thenReturn(book);
        when(tagService.findTagByName(tagName)).thenReturn(tag);
        when(tagMapper.toDto(tag)).thenReturn(expected);

        TagDto result = commonsService.addBookToTag(tagName, urlId);

        verify(commonsRepository).addBookToTag(tag.getId(), book.getId());
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should throw NoSuchElementException if Tag not found removeBookFromTag()")
    void removeBookFromTagWhenTagNotFound() {
        String tagName = "tagName";
        String urlId = "url-id";
        when(bookService.getBookEntityById(urlId)).thenReturn(new Book());
        when(tagService.findTagByName(tagName)).thenThrow(new NoSuchElementException());

        assertThatThrownBy(() -> commonsService.removeBookFromTag(tagName, urlId))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Should throw NoSuchElementException if Book not found removeBookFromTag()")
    void removeBookFromTagWhenBookNotFound() {
        String tagName = "tagName";
        String urlId = "url-id";
        when(bookService.getBookEntityById(urlId)).thenThrow(new NoSuchElementException());

        assertThatThrownBy(() -> commonsService.removeBookFromTag(tagName, urlId))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Should remove a book from tag correctly removeBookFromTag()")
    void removeBookFromTagWhenEverythingOk() {
        String tagName = "tagName";
        String urlId = "author-title";
        Book book = new Book(urlId, "title", "author");
        book.setId(101L);
        Tag tag = new Tag(tagName);
        tag.setId(107L);
        tag.setBooks(List.of(book));
        TagDto expected = new TagDto(tagName, 0L);
        when(bookService.getBookEntityById(urlId)).thenReturn(book);
        when(tagService.findTagByName(tagName)).thenReturn(tag);
        when(tagMapper.toDto(tag)).thenReturn(expected);

        TagDto result = commonsService.removeBookFromTag(tagName, urlId);

        verify(commonsRepository).removeBookFromTag(tag.getId(), book.getId());
        assertThat(result).isEqualTo(expected);
    }
}
