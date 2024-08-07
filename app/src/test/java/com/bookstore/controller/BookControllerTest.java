package com.bookstore.controller;

import com.bookstore.dto.BookDto;
import com.bookstore.dto.BookDtoBuilder;
import com.bookstore.model.Tag;
import com.bookstore.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should get all books correctly getAllBooks()")
    void shouldGetAllBooksCorrectly() throws Exception {
        BookDto bookDto1 = new BookDtoBuilder().id(1L).urlId("author-1-title-1").title("Title 1").author("Author 1").stars(2).price(new BigDecimal("2.99")).favorite(false).imageUrl("image/1").tags(List.of(new Tag("tag1"))).build();
        BookDto bookDto2 = new BookDtoBuilder().id(2L).urlId("author-2-title-2").title("Title 2").author("Author 2").stars(5).price(new BigDecimal("3.99")).favorite(true).imageUrl("image/2").tags(List.of()).build();
        when(bookService.getAllBooks()).thenReturn(List.of(bookDto1, bookDto2));
        String expected = "[{\"id\":1,\"urlId\":\"author-1-title-1\",\"title\":\"Title 1\",\"author\":\"Author 1\",\"stars\":2,\"price\":2.99,\"favorite\":false,\"imageUrl\":\"image/1\",\"tags\":[\"tag1\"]},{\"id\":2,\"urlId\":\"author-2-title-2\",\"title\":\"Title 2\",\"author\":\"Author 2\",\"stars\":5,\"price\":3.99,\"favorite\":true,\"imageUrl\":\"image/2\",\"tags\":null}]" ;

        RequestBuilder request = MockMvcRequestBuilders.get("/books");
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return empty list of books getAllBooks()")
    void getAllBooksIfThereAreNoReturned() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of());
        String expected = "[]";

        RequestBuilder request = MockMvcRequestBuilders.get("/books");
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should handle NoSuchElementException getBookById()")
    void getBookByIdWhenIdNotExists() throws Exception {
        String urlId = "author-title";
        when(bookService.getBookById(urlId)).thenThrow(new NoSuchElementException());

        RequestBuilder request = MockMvcRequestBuilders.get("/books/" + urlId);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(404);
        assertThat(result.getResponse().getContentAsString()).isEqualTo("An object with specified property does not exist");
    }

    @Test
    @DisplayName("Should return a book by id correctly getBookById()")
    void getBookByIdCorrectly() throws Exception {
        String urlId = "author-1-title-1";
        BookDto bookDto = new BookDtoBuilder().id(1L).urlId(urlId).title("Title 1").author("Author 1").stars(2).price(new BigDecimal("2.99")).favorite(false).imageUrl("image/1").tags(List.of(new Tag("tag1"))).build();
        when(bookService.getBookById(urlId)).thenReturn(bookDto);
        String expected = "{\"id\":1,\"urlId\":\"author-1-title-1\",\"title\":\"Title 1\",\"author\":\"Author 1\",\"stars\":2,\"price\":2.99,\"favorite\":false,\"imageUrl\":\"image/1\",\"tags\":[\"tag1\"]}";

        RequestBuilder request = MockMvcRequestBuilders.get("/books/" + urlId);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return an empty list if books with a specified tag do not exist or the tag does not exist getAllBooksByTag()")
    void getBooksByTagWhenTagNotExist() throws Exception {
        String tagName = "tag1";
        when(bookService.getAllBooksByTag(tagName)).thenReturn(List.of());
        String expected = "[]";

        RequestBuilder request = MockMvcRequestBuilders.get("/books/tag/" + tagName);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return a list of books with a specified tag correctly getAllBooksByTag()")
    void getBooksByTagCorrect() throws Exception {
        String tagName = "tag1";
        Tag tag1 = new Tag(tagName);
        BookDto bookDto1 = new BookDtoBuilder().id(1L).urlId("author-1-title-1").title("Title 1").author("Author 1").stars(2).price(new BigDecimal("2.99")).favorite(false).imageUrl("image/1").tags(List.of(tag1)).build();
        BookDto bookDto2 = new BookDtoBuilder().id(2L).urlId("author-2-title-2").title("Title 2").author("Author 2").stars(5).price(new BigDecimal("3.99")).favorite(true).imageUrl("image/2").tags(List.of(tag1)).build();
        when(bookService.getAllBooksByTag(tagName)).thenReturn(List.of(bookDto1, bookDto2));
        String expected = "[{\"id\":1,\"urlId\":\"author-1-title-1\",\"title\":\"Title 1\",\"author\":\"Author 1\",\"stars\":2,\"price\":2.99,\"favorite\":false,\"imageUrl\":\"image/1\",\"tags\":[\"tag1\"]},{\"id\":2,\"urlId\":\"author-2-title-2\",\"title\":\"Title 2\",\"author\":\"Author 2\",\"stars\":5,\"price\":3.99,\"favorite\":true,\"imageUrl\":\"image/2\",\"tags\":[\"tag1\"]}]";

        RequestBuilder request = MockMvcRequestBuilders.get("/books/tag/" + tagName);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return an empty list if books with a specified phrase in urlId do not exist getAllBooksByTag()")
    void getBooksByPhraseWhenPhraseNotExists() throws Exception {
        String phrase = "tag1";
        when(bookService.getAllBooksByPhrase(phrase)).thenReturn(List.of());
        String expected = "[]";

        RequestBuilder request = MockMvcRequestBuilders.get("/books/phrase/" + phrase);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return a list of books with a specified phrase in urlId correctly getAllBooksByPhrase()")
    void getBooksByPhraseCorrect() throws Exception {
        String phrase = "author";
        BookDto bookDto1 = new BookDtoBuilder().id(1L).urlId("author-1-title-1").title("Title 1").author("Author 1").stars(2).price(new BigDecimal("2.99")).favorite(false).imageUrl("image/1").tags(List.of()).build();
        BookDto bookDto2 = new BookDtoBuilder().id(2L).urlId("author-2-title-2").title("Title 2").author("Author 2").stars(5).price(new BigDecimal("3.99")).favorite(true).imageUrl("image/2").tags(List.of()).build();
        when(bookService.getAllBooksByPhrase(phrase)).thenReturn(List.of(bookDto1, bookDto2));
        String expected = "[{\"id\":1,\"urlId\":\"author-1-title-1\",\"title\":\"Title 1\",\"author\":\"Author 1\",\"stars\":2,\"price\":2.99,\"favorite\":false,\"imageUrl\":\"image/1\",\"tags\":null},{\"id\":2,\"urlId\":\"author-2-title-2\",\"title\":\"Title 2\",\"author\":\"Author 2\",\"stars\":5,\"price\":3.99,\"favorite\":true,\"imageUrl\":\"image/2\",\"tags\":null}]";

        RequestBuilder request = MockMvcRequestBuilders.get("/books/phrase/" + phrase);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return error when body not valid addBook()")
    void addBookWhenBodyStateNotValid() throws Exception {
        BookDto bookDto = new BookDtoBuilder().title("").author("").stars(2).price(new BigDecimal("2.99")).favorite(false).imageUrl("image/1").tags(List.of()).build();
        String input = objectMapper.writeValueAsString(bookDto);
        String expected = "{\"author\":\"Author should not be blank\",\"title\":\"Title should not be blank\"}";

        RequestBuilder request = MockMvcRequestBuilders
                .post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return error when body empty/null addBook()")
    void addBookWhenBodyEmpty() throws Exception {
        String input = "";
        String expected = "Invalid request body";

        RequestBuilder request = MockMvcRequestBuilders
                .post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return error when trying to add an entity with already existing author and title (results in duplicated urlId) addBook()")
    void addBookWhenDuplicatedId() throws Exception {
        BookDto bookDto = new BookDtoBuilder().title("title").author("author").stars(2).price(new BigDecimal("2.99")).favorite(false).imageUrl("image/1").tags(List.of()).build();
        String input = objectMapper.writeValueAsString(bookDto);
        String errorMessage = "could not execute statement [Duplicate entry";
        Mockito.doAnswer(invocation -> {
            throw new SQLIntegrityConstraintViolationException(errorMessage);
        }).when(bookService).addBook(bookDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result.getResponse().getContentAsString()).contains(errorMessage);
    }

    @Test
    @DisplayName("Should add a book correctly addBook()")
    void addBookCorrect() throws Exception {
        BookDto bookDto = new BookDtoBuilder().title("title").author("author").stars(2).price(new BigDecimal("2.99")).favorite(false).imageUrl("image/1").tags(List.of()).build();
        BookDto created = new BookDtoBuilder().id(1L).urlId("author-title").title("title").author("author").stars(2).price(new BigDecimal("2.99")).favorite(false).imageUrl("image/1").tags(List.of()).build();
        String input = objectMapper.writeValueAsString(bookDto);
        String expected = "{\"id\":1,\"urlId\":\"author-title\",\"title\":\"title\",\"author\":\"author\",\"stars\":2,\"price\":2.99,\"favorite\":false,\"imageUrl\":\"image/1\",\"tags\":null}";
        when(bookService.addBook(bookDto)).thenReturn(created);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(input);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(201);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return NOT_FOUND if book does not exist by urlId deleteBook()")
    void deleteBookWhenBookWithProvidedIdDoesNotExist() throws Exception {
        String urlId = "not-existing-url-id";
        doAnswer(invocation -> {
            throw new NoSuchElementException();
        }).when(bookService).deleteABook(urlId);

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/books/" + urlId);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(404);
        assertThat(result.getResponse().getContentAsString()).isEqualTo("An object with specified property does not exist");
    }

    @Test
    @DisplayName("Should delete a book by urlId correctly deleteBook()")
    void deleteBookCorrect() throws Exception {
        String urlId = "url-id";

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/books/" + urlId);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo("Record with id: \"" + urlId + "\" successfully removed");
    }
}