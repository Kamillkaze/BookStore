package com.bookstore.service;

import com.bookstore.dto.BookDto;
import com.bookstore.dto.BookDtoBuilder;
import com.bookstore.mapper.BookMapper;
import com.bookstore.model.Book;
import com.bookstore.model.BookBuilder;
import com.bookstore.repository.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Test
    @DisplayName("Should add book correctly")
    void addBookWhenEverythingOk() {
        BookDto bookDto = new BookDtoBuilder().urlId("author1-title1").author("author1").title("title1").build();
        BookDto expected = new BookDtoBuilder().id(1L).urlId("author1-title1").author("author1").title("title1").build();
        Book book = new Book("author1-title1", "author1", "title1");
        Book bookSaved = new BookBuilder().id(1L).urlId("author1", "title1").author("author1").title("title1").build();
        when(bookMapper.toEntity(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(bookSaved);
        when(bookMapper.toDto(bookSaved)).thenReturn(expected);

        BookDto actual = bookService.addBook(bookDto);

        assertThat(actual).isEqualTo(expected);
        verify(bookMapper).toEntity(bookDto);
        verify(bookRepository).save(book);
        verify(bookMapper).toDto(bookSaved);
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when book does not exist by urlId")
    void deleteBookByUrlIdWhenBookDoesNotExist() {
        String urlId = "nonExistentUrlId";
        when(bookRepository.deleteByUrlId(urlId)).thenReturn(0);

        assertThatThrownBy(() -> bookService.deleteABook(urlId))
                .isInstanceOf(NoSuchElementException.class);
        verify(bookRepository).deleteByUrlId(urlId);
    }

    @Test
    @DisplayName("Should call deleteByUrlId method from BookRepository")
    void deleteBookByUrlIdWhenCorrectInputPassed() {
        String urlId = "url-id";
        when(bookRepository.deleteByUrlId(urlId)).thenReturn(1);

        bookService.deleteABook(urlId);

        verify(bookRepository).deleteByUrlId(urlId);
    }

    @Test
    @DisplayName("Should return empty list when repository empty")
    void getAllBooksWhenRepositoryIsEmpty() {
        List<Book> books = List.of();
        when(bookRepository.findAllBooks()).thenReturn(books);

        List<BookDto> result = bookService.getAllBooks();

        assertThat(result).isEmpty();
    }


    @Test
    @DisplayName("Should return a list of BookDto correctly")
    void getAllBooksWhenEverythingOk() {
        List<BookDto> expected = List.of(
                new BookDtoBuilder().urlId("url-id-1").title("title-1").author("author-1").build(),
                new BookDtoBuilder().urlId("url-id-2").title("title-2").author("author-2").build(),
                new BookDtoBuilder().urlId("url-id-3").title("title-3").author("author-3").build()
        );
        List<Book> books = List.of(
                new Book("url-id-1", "title-1", "author-1"),
                new Book("url-id-2", "title-2", "author-2"),
                new Book("url-id-3", "title-3", "author-3")
        );
        when(bookRepository.findAllBooks()).thenReturn(books);
        when(bookMapper.toDto(books.get(0))).thenReturn(expected.get(0));
        when(bookMapper.toDto(books.get(1))).thenReturn(expected.get(1));
        when(bookMapper.toDto(books.get(2))).thenReturn(expected.get(2));

        List<BookDto> result = bookService.getAllBooks();

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should throw NoSuchElementException when repository returns empty Optional")
    void getBookEntityByIdWhenRepositoryReturnsEmptyOptional() {
        String urlId = "url-id";
        when(bookRepository.findByUrlId(urlId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getBookEntityById(urlId))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Should return a correct Book when repository returns an Optional with it")
    void getBookEntityByIdWhenRepositoryReturnsPresentOptional() {
        String urlId = "url-id";
        Book book = new Book("url-id-1", "title-1", "author-1");
        when(bookRepository.findByUrlId(urlId)).thenReturn(Optional.of(book));

        Book result = bookService.getBookEntityById(urlId);

        assertThat(result).isEqualTo(book);
    }

    @Test
    @DisplayName("Should call mapper.toDto() and return a correct object")
    void getBookByIdWhenCorrectInputPassed() {
        String urlId = "url-id";
        Book book = new Book("url-id-1", "title-1", "author-1");
        when(bookRepository.findByUrlId(urlId)).thenReturn(Optional.of(book));

        bookService.getBookById(urlId);

        verify(bookMapper).toDto(book);
    }

    @Test
    @DisplayName("Should return empty list if tag does not exist")
    void getAllBooksByTagWhenRepositoryIsEmpty() {
        List<Book> books = List.of();
        String tagName = "tag-name";
        when(bookRepository.findByTagName(tagName)).thenReturn(books);

        List<BookDto> result = bookService.getAllBooksByTag(tagName);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return list of BookDtos correctly when Tag found")
    void getAllBooksByTag() {
        List<BookDto> expected = List.of(
                new BookDtoBuilder().urlId("url-id-1").title("title-1").author("author-1").build(),
                new BookDtoBuilder().urlId("url-id-2").title("title-2").author("author-2").build(),
                new BookDtoBuilder().urlId("url-id-3").title("title-3").author("author-3").build()
        );
        List<Book> books = List.of(
                new Book("url-id-1", "title-1", "author-1"),
                new Book("url-id-2", "title-2", "author-2"),
                new Book("url-id-3", "title-3", "author-3")
        );
        String tagName = "tag-name";
        when(bookRepository.findByTagName(tagName)).thenReturn(books);
        when(bookMapper.toDto(books.get(0))).thenReturn(expected.get(0));
        when(bookMapper.toDto(books.get(1))).thenReturn(expected.get(1));
        when(bookMapper.toDto(books.get(2))).thenReturn(expected.get(2));

        List<BookDto> result = bookService.getAllBooksByTag(tagName);

        assertThat(result).hasSameElementsAs(expected);
    }

    @Test
    @DisplayName("Should return empty list if phrase does not exist")
    void getAllBooksByPhraseWhenRepositoryIsEmpty() {
        List<Book> books = List.of();
        String tagName = "tag-name";
        when(bookRepository.findByUrlIdContainingPhrase(tagName)).thenReturn(books);

        List<BookDto> result = bookService.getAllBooksByPhrase(tagName);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return list of BookDtos correctly when phrase found")
    void getAllBooksByPhrase() {
        List<BookDto> expected = List.of(
                new BookDtoBuilder().urlId("url-id-1").title("title-1").author("author-1").build(),
                new BookDtoBuilder().urlId("url-id-2").title("title-2").author("author-2").build(),
                new BookDtoBuilder().urlId("url-id-3").title("title-3").author("author-3").build()
        );
        List<Book> books = List.of(
                new Book("url-id-1", "title-1", "author-1"),
                new Book("url-id-2", "title-2", "author-2"),
                new Book("url-id-3", "title-3", "author-3")
        );
        String phrase = "tag-name";
        when(bookRepository.findByUrlIdContainingPhrase(phrase)).thenReturn(books);
        when(bookMapper.toDto(books.get(0))).thenReturn(expected.get(0));
        when(bookMapper.toDto(books.get(1))).thenReturn(expected.get(1));
        when(bookMapper.toDto(books.get(2))).thenReturn(expected.get(2));

        List<BookDto> result = bookService.getAllBooksByPhrase(phrase);

        assertThat(result).hasSameElementsAs(expected);
    }
}