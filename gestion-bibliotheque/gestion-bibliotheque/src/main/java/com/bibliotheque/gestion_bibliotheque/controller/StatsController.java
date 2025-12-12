package com.bibliotheque.gestion_bibliotheque.controller;

import com.bibliotheque.gestion_bibliotheque.repository.AuthorRepository;
import com.bibliotheque.gestion_bibliotheque.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stats")
public class StatsController {
    @Autowired
    private StatsService statsService;

    @GetMapping("/books-per-category")
    public Map<?, ?> booksPerCategory() {
        return statsService.booksPerCategory();
    }

    @GetMapping("/top-authors")
    public List<AuthorRepository.TopAuthorProjection> topAuthors(@RequestParam(defaultValue = "3") int limit) {
        return statsService.topAuthors(limit);
    }
}
