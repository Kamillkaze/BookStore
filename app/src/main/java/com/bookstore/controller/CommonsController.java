package com.bookstore.controller;

import com.bookstore.dto.TagDto;
import com.bookstore.service.CommonsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/commons")
public class CommonsController {

    private final CommonsService commonsService;

    public CommonsController(CommonsService commonsService) {
        this.commonsService = commonsService;
    }

    @PostMapping(value = "/tags/{tagName}")
    public ResponseEntity<TagDto> addBookToTag(@PathVariable String tagName, @RequestParam String bookUrlId) {
        return ResponseEntity.ok(commonsService.addBookToTag(tagName, bookUrlId));
    }
}
