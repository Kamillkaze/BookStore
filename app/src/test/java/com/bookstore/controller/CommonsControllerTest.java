package com.bookstore.controller;

import com.bookstore.dto.TagDto;
import com.bookstore.service.CommonsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = CommonsController.class)
class CommonsControllerTest {

    @MockBean
    private CommonsService commonsService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should handle NoSuchElementException thrown in service addBookToTag()")
    void addBookToTagWhenNoSuchElementThrown() throws Exception {
        String tagName = "tag1";
        String bookUrlId = "url-id";
        when(commonsService.addBookToTag(tagName, bookUrlId)).thenThrow(new NoSuchElementException());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/commons/tag/" + tagName)
                .param("bookUrlId", bookUrlId);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(404);
        assertThat(result.getResponse().getContentAsString()).isEqualTo("An object with specified property does not exist");
    }

    @Test
    @DisplayName("Should handle lack of bookUrlId parameter addBookToTag()")
    void addBookToTagWhenNoBookUrlId() throws Exception {
        String tagName = "tag1";

        RequestBuilder request = MockMvcRequestBuilders
                .post("/commons/tag/" + tagName);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result.getResponse().getContentAsString()).isEqualTo("Missing request parameter: bookUrlId");
    }

    @Test
    @DisplayName("Should add book to tag correctly addBookToTag()")
    void addBookToTagCorrect() throws Exception {
        String tagName = "tag1";
        String bookUrlId = "url-id";
        TagDto tagDto = new TagDto("tag1", 1L);
        String expected = objectMapper.writeValueAsString(tagDto);
        when(commonsService.addBookToTag(tagName, bookUrlId)).thenReturn(tagDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/commons/tag/" + tagName)
                .param("bookUrlId", bookUrlId);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should handle NoSuchElementException thrown in service removeBookFromTag()")
    void removeBookFromTagWhenNoSuchElementThrown() throws Exception {
        String tagName = "tag1";
        String bookUrlId = "url-id";
        when(commonsService.removeBookFromTag(tagName, bookUrlId)).thenThrow(new NoSuchElementException());

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/commons/tag/" + tagName)
                .param("bookUrlId", bookUrlId);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(404);
        assertThat(result.getResponse().getContentAsString()).isEqualTo("An object with specified property does not exist");
    }

    @Test
    @DisplayName("Should handle lack of bookUrlId parameter removeBookFromTag()")
    void removeBookFromTagWhenNoBookUrlId() throws Exception {
        String tagName = "tag1";

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/commons/tag/" + tagName);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result.getResponse().getContentAsString()).isEqualTo("Missing request parameter: bookUrlId");

    }

    @Test
    @DisplayName("Should delete a book from tag correctly removeBookFromTag()")
    void removeBookFromTagCorrect() throws Exception {
        String tagName = "tag1";
        String bookUrlId = "url-id";
        TagDto tagDto = new TagDto("tag1", 0L);
        String expected = objectMapper.writeValueAsString(tagDto);
        when(commonsService.removeBookFromTag(tagName, bookUrlId)).thenReturn(tagDto);

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/commons/tag/" + tagName)
                .param("bookUrlId", bookUrlId);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }
}