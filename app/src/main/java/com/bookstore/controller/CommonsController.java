package com.bookstore.controller;

import com.bookstore.dto.TagDto;
import com.bookstore.service.CommonsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/commons")
public class CommonsController {

    private final CommonsService commonsService;

    public CommonsController(CommonsService commonsService) {
        this.commonsService = commonsService;
    }

    @PostMapping(value = "/tag/{tagName}")
    public ResponseEntity<TagDto> addBookToTag(@PathVariable String tagName, @RequestParam String bookUrlId) {
        return ResponseEntity.ok(commonsService.addBookToTag(tagName, bookUrlId));
    }

    @DeleteMapping(value = "/tag/{tagName}")
    public ResponseEntity<TagDto> removeBookFromTag(@PathVariable String tagName, @RequestParam String bookUrlId) {
        return ResponseEntity.ok(commonsService.removeBookFromTag(tagName, bookUrlId));
    }
}
