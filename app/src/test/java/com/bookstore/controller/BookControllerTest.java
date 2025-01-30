package com.bookstore.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

import com.bookstore.dto.BookDto;
import com.bookstore.security.SecurityConfig;
import com.bookstore.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = BookController.class)
class BookControllerTest {

    @TestConfiguration
    static class MockDataSourceConfig {
        @Bean
        @Primary
        public DataSource dataSource() {
            return mock(DataSource.class);
        }

        @Bean
        public ObjectMapper objectMapper() {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper;
        }

        @Bean
        public TestUtils testUtils() {
            return new TestUtils();
        }
    }

    @MockBean private BookService bookService;

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @Autowired private TestUtils testUtils;

    @Test
    @DisplayName("Should get all books correctly getAllBooks()")
    @WithMockUser
    void shouldGetAllBooksCorrectly() throws Exception {
        BookDto bookDto1 = testUtils.readJsonFile("book-dto-1-with-tag1.json", BookDto.class);
        BookDto bookDto2 = testUtils.readJsonFile("book-dto-2.json", BookDto.class);
        BookDto[] expectedDto = testUtils.readJsonFile("response-all-books.json", BookDto[].class);
        String expected = objectMapper.writeValueAsString(expectedDto);
        when(bookService.getAllBooks()).thenReturn(List.of(bookDto1, bookDto2));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/books");
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return empty list of books getAllBooks()")
    @WithMockUser
    void getAllBooksIfThereAreNoReturned() throws Exception {
        when(bookService.getAllBooks()).thenReturn(List.of());
        String expected = "[]";

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/books");
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should handle NoSuchElementException getBookById()")
    @WithMockUser
    void getBookByIdWhenIdNotExists() throws Exception {
        String urlId = "author-title";
        when(bookService.getBookById(urlId)).thenThrow(new NoSuchElementException());

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/books/" + urlId);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(404);
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo("An object with specified property does not exist");
    }

    @Test
    @DisplayName("Should return a book by id correctly getBookById()")
    @WithMockUser
    void getBookByIdCorrectly() throws Exception {
        BookDto bookDto = testUtils.readJsonFile("book-dto-1-with-tag1.json", BookDto.class);
        BookDto expectedDto = testUtils.readJsonFile("response-book-by-id.json", BookDto.class);
        String expected = objectMapper.writeValueAsString(expectedDto);
        String urlId = bookDto.getUrlId();
        when(bookService.getBookById(urlId)).thenReturn(bookDto);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/books/" + urlId);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName(
            "Should return an empty list if books with a specified tag do not exist or the tag does not exist getAllBooksByTag()")
    @WithMockUser
    void getBooksByTagWhenTagNotExist() throws Exception {
        String tagName = "tag1";
        String expected = "[]";
        when(bookService.getAllBooksByTag(tagName)).thenReturn(List.of());

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/books/tag/" + tagName);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return a list of books with a specified tag correctly getAllBooksByTag()")
    @WithMockUser
    void getBooksByTagCorrect() throws Exception {
        String tagName = "tag1";
        BookDto bookDto1 = testUtils.readJsonFile("book-dto-1-with-tag1.json", BookDto.class);
        BookDto bookDto2 = testUtils.readJsonFile("book-dto-2-with-tag1.json", BookDto.class);
        BookDto[] expectedDto = testUtils.readJsonFile("response-books-by-tag.json", BookDto[].class);
        String expected = objectMapper.writeValueAsString(expectedDto);
        when(bookService.getAllBooksByTag(tagName)).thenReturn(List.of(bookDto1, bookDto2));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/books/tag/" + tagName);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName(
            "Should return an empty list if books with a specified phrase in urlId do not exist getAllBooksByTag()")
    @WithMockUser
    void getBooksByPhraseWhenPhraseNotExists() throws Exception {
        String phrase = "tag1";
        String expected = "[]";
        when(bookService.getAllBooksByPhrase(phrase)).thenReturn(List.of());

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/books/phrase/" + phrase);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName(
            "Should return a list of books with a specified phrase in urlId correctly getAllBooksByPhrase()")
    @WithMockUser
    void getBooksByPhraseCorrect() throws Exception {
        String phrase = "author";
        BookDto bookDto1 = testUtils.readJsonFile("book-dto-1.json", BookDto.class);
        BookDto bookDto2 = testUtils.readJsonFile("book-dto-2.json", BookDto.class);
        BookDto[] expectedDto =
                testUtils.readJsonFile("response-books-by-phrase.json", BookDto[].class);
        String expected = objectMapper.writeValueAsString(expectedDto);
        when(bookService.getAllBooksByPhrase(phrase)).thenReturn(List.of(bookDto1, bookDto2));

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/books/phrase/" + phrase);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return error when body not valid addBook()")
    @WithMockUser(roles = "ADMIN")
    void addBookWhenBodyStateNotValid() throws Exception {
        BookDto bookDto = testUtils.readJsonFile("book-dto-blank-properties.json", BookDto.class);
        String input = objectMapper.writeValueAsString(bookDto);
        String expected =
                "{\"author\":\"Author should not be blank\",\"title\":\"Title should not be blank\"}";

        RequestBuilder request =
                MockMvcRequestBuilders.post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return error when body empty/null addBook()")
    @WithMockUser(roles = "ADMIN")
    void addBookWhenBodyEmpty() throws Exception {
        String input = "";
        String expected = "Invalid request body";

        RequestBuilder request =
                MockMvcRequestBuilders.post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName(
            "Should return error when trying to add an entity with already existing author and title (results in duplicated urlId) addBook()")
    @WithMockUser(roles = "ADMIN")
    void addBookWhenDuplicatedId() throws Exception {
        BookDto bookDto = testUtils.readJsonFile("book-dto-1.json", BookDto.class);
        String input = objectMapper.writeValueAsString(bookDto);
        String errorMessage = "could not execute statement [Duplicate entry";
        Mockito.doAnswer(
                        invocation -> {
                            throw new SQLIntegrityConstraintViolationException(errorMessage);
                        })
                .when(bookService)
                .addBook(bookDto);

        RequestBuilder request =
                MockMvcRequestBuilders.post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result.getResponse().getContentAsString()).contains(errorMessage);
    }

    @Test
    @DisplayName("Should add a book correctly addBook()")
    @WithMockUser(roles = "ADMIN")
    void addBookCorrect() throws Exception {
        BookDto bookDto = testUtils.readJsonFile("book-dto-1-no-url-id.json", BookDto.class);
        BookDto created = testUtils.readJsonFile("book-dto-1.json", BookDto.class);
        BookDto expectedDto = testUtils.readJsonFile("response-add-book.json", BookDto.class);
        String input = objectMapper.writeValueAsString(bookDto);
        String expected = objectMapper.writeValueAsString(expectedDto);
        when(bookService.addBook(bookDto)).thenReturn(created);

        RequestBuilder request =
                MockMvcRequestBuilders.post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(input);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(201);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return NOT_FOUND if book does not exist by urlId deleteBook()")
    @WithMockUser(roles = "ADMIN")
    void deleteBookWhenBookWithProvidedIdDoesNotExist() throws Exception {
        String urlId = "not-existing-url-id";
        doAnswer(
                        invocation -> {
                            throw new NoSuchElementException();
                        })
                .when(bookService)
                .deleteABook(urlId);

        RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/books/" + urlId);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(404);
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo("An object with specified property does not exist");
    }

    @Test
    @DisplayName("Should delete a book by urlId correctly deleteBook()")
    @WithMockUser(roles = "ADMIN")
    void deleteBookCorrect() throws Exception {
        String urlId = "url-id";

        RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/books/" + urlId);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo("Record with id: \"" + urlId + "\" successfully removed");
    }
}
