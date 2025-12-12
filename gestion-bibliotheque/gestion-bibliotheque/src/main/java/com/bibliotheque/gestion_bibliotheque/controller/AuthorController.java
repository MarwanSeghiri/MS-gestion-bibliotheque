package com.bibliotheque.gestion_bibliotheque.controller;

import com.bibliotheque.gestion_bibliotheque.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bibliotheque.gestion_bibliotheque.service.AuthorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/authors")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @GetMapping
    public Iterable<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id) {
        Author a = authorService.getAuthorById(id);
        if (a == null) {
            java.util.Map<String, Object> body = new java.util.HashMap<>();
            body.put("status", 404);
            body.put("error", "Not Found");
            body.put("message", "Author not found");
            return ResponseEntity.status(404).body(body);
        }
        return ResponseEntity.ok(a);
    }

    @PostMapping
    public ResponseEntity<?> createAuthor(@Valid @RequestBody Author author) {
        return ResponseEntity.ok(authorService.saveAuthor(author));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @Valid @RequestBody Author author) {
        Author updated = authorService.updateAuthor(id, author);
        if (updated == null) {
            java.util.Map<String, Object> body = new java.util.HashMap<>();
            body.put("status", 404);
            body.put("error", "Not Found");
            body.put("message", "Author not found");
            return ResponseEntity.status(404).body(body);
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        Author a = authorService.getAuthorById(id);
        if (a == null) {
            java.util.Map<String, Object> body = new java.util.HashMap<>();
            body.put("status", 404);
            body.put("error", "Not Found");
            body.put("message", "Author not found");
            return ResponseEntity.status(404).body(body);
        }
        authorService.deleteAuthorById(id);
        return ResponseEntity.ok().build();
    }
}
