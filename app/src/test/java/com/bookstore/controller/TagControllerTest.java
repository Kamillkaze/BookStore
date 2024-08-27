package com.bookstore.controller;

import com.bookstore.dto.TagDto;
import com.bookstore.service.TagService;
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

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = TagController.class)
class TagControllerTest {

    @MockBean
    private TagService tagService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should return error when trying to add an entity with already existing name addNewTag()")
    void addNewTagWhenDuplicatedId() throws Exception {
        String tagName = "tag1";
        String errorMessage = "could not execute statement [Duplicate entry";
        doAnswer(invocation -> {
            throw new SQLIntegrityConstraintViolationException(errorMessage);
        }).when(tagService).addNewTag(tagName);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/tags/" + tagName);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result.getResponse().getContentAsString()).contains(errorMessage);
    }

    @Test
    @DisplayName("Should add a new tag correctly addNewTag()")
    void addNewTagCorrect() throws Exception {
        String tagName = "tag1";
        TagDto tagDto = new TagDto(tagName, 0L);
        String expected = objectMapper.writeValueAsString(tagDto);
        when(tagService.addNewTag(tagName)).thenReturn(tagDto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/v1/tags/" + tagName);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return error code when trying to delete non-existing tag deleteATag()")
    void deleteATagWhenNonExistingTag() throws Exception {
        String tagName = "tag1";
        String expected = "An object with specified property does not exist";
        doAnswer(invocation -> {
            throw new NoSuchElementException();
        }).when(tagService).deleteATag(tagName);

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/api/v1/tags/" + tagName);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(404);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should delete a tag correctly deleteATag()")
    void deleteATagCorrect() throws Exception {
        String tagName = "tag1";
        String expected = "Tag \"" + tagName + "\" successfully removed.";

        RequestBuilder request = MockMvcRequestBuilders
                .delete("/api/v1/tags/" + tagName);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return a list of all tags correctly getAll()")
    void getAllTags() throws Exception {
        List<TagDto> tagDtos = List.of(new TagDto("tag1", 5L), new TagDto("tag2", 1L));
        String expected = objectMapper.writeValueAsString(tagDtos);
        when(tagService.getAllTags()).thenReturn(tagDtos);

        RequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/tags");
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }
}