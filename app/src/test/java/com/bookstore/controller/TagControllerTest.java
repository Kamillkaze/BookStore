package com.bookstore.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

import com.bookstore.dto.TagDto;
import com.bookstore.security.SecurityConfig;
import com.bookstore.service.TagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Import(SecurityConfig.class)
@WebMvcTest(controllers = TagController.class)
class TagControllerTest {

    @TestConfiguration
    static class MockDataSourceConfig {
        @Bean
        @Primary
        public DataSource dataSource() {
            return mock(DataSource.class);
        }
    }

    @MockBean private TagService tagService;

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @Test
    @DisplayName(
            "Should return error when trying to add an entity with already existing name addNewTag()")
    @WithMockUser(roles = "ADMIN")
    void addNewTagWhenDuplicatedId() throws Exception {
        String tagName = "tag1";
        String errorMessage = "could not execute statement [Duplicate entry";
        doAnswer(
                        invocation -> {
                            throw new SQLIntegrityConstraintViolationException(errorMessage);
                        })
                .when(tagService)
                .addNewTag(tagName);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/tags/" + tagName);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(400);
        assertThat(result.getResponse().getContentAsString()).contains(errorMessage);
    }

    @Test
    @DisplayName("Should add a new tag correctly addNewTag()")
    @WithMockUser(roles = "ADMIN")
    void addNewTagCorrect() throws Exception {
        String tagName = "tag1";
        TagDto tagDto = new TagDto(tagName, 0L);
        String expected = objectMapper.writeValueAsString(tagDto);
        when(tagService.addNewTag(tagName)).thenReturn(tagDto);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/v1/tags/" + tagName);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return error code when trying to delete non-existing tag deleteATag()")
    @WithMockUser(roles = "ADMIN")
    void deleteATagWhenNonExistingTag() throws Exception {
        String tagName = "tag1";
        String expected = "An object with specified property does not exist";
        doAnswer(
                        invocation -> {
                            throw new NoSuchElementException();
                        })
                .when(tagService)
                .deleteATag(tagName);

        RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/tags/" + tagName);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(404);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should delete a tag correctly deleteATag()")
    @WithMockUser(roles = "ADMIN")
    void deleteATagCorrect() throws Exception {
        String tagName = "tag1";
        String expected = "Tag \"" + tagName + "\" successfully removed.";

        RequestBuilder request = MockMvcRequestBuilders.delete("/api/v1/tags/" + tagName);
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return a list of all tags correctly getAll()")
    @WithMockUser
    void getAllTags() throws Exception {
        List<TagDto> tagDtos = List.of(new TagDto("tag1", 5L), new TagDto("tag2", 1L));
        String expected = objectMapper.writeValueAsString(tagDtos);
        when(tagService.getAllTags()).thenReturn(tagDtos);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/v1/tags");
        MvcResult result = mockMvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(200);
        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
    }
}
