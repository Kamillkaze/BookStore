package com.bookstore.controller;

import com.bookstore.dto.TagDto;
import com.bookstore.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@CrossOrigin(origins = "http://localhost:4200")
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
    public ResponseEntity<TagDto> deleteATag(@PathVariable String tagName) {
        return ResponseEntity.ok(tagService.deleteATag(tagName));
    }

    @GetMapping
    public ResponseEntity<List<TagDto>> getAll() {
        return ResponseEntity.ok(tagService.getAll());
    }
}
