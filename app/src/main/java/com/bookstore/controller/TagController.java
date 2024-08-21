package com.bookstore.controller;

import com.bookstore.dto.TagDto;
import com.bookstore.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/{tagName}")
    public ResponseEntity<TagDto> addNewTag(@PathVariable String tagName) {
        return ResponseEntity.ok(tagService.addNewTag(tagName));
    }

    @DeleteMapping(value = "/{tagName}")
    public ResponseEntity<String> deleteATag(@PathVariable String tagName) {
        tagService.deleteATag(tagName);
        return ResponseEntity.ok("Tag \"" + tagName + "\" successfully removed.");
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> getAll() {
        return ResponseEntity.ok(tagService.getAllTags());
    }
}
