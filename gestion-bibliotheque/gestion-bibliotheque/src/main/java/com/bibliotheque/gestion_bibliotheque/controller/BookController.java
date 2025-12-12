package com.bibliotheque.gestion_bibliotheque.controller;

import com.bibliotheque.gestion_bibliotheque.domain.Book;
import com.bibliotheque.gestion_bibliotheque.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.bibliotheque.gestion_bibliotheque.service.BookService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @GetMapping
    public Page<Book> getAllBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Category category,
            @RequestParam(required = false) Integer yearFrom,
            @RequestParam(required = false) Integer yearTo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort
    ) {
        Sort sortObj = Sort.unsorted();
        if (sort != null && !sort.isBlank()) {
            String[] parts = sort.split(",");
            String field = parts[0];
            Sort.Direction dir = (parts.length > 1 && "desc".equalsIgnoreCase(parts[1])) ? Sort.Direction.DESC : Sort.Direction.ASC;
            sortObj = Sort.by(dir, field);
        }
        PageRequest pr = PageRequest.of(page, size, sortObj);
        return bookService.searchBooks(title, authorId, category, yearFrom, yearTo, pr);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBookById(@PathVariable Long id) {
        Book b = bookService.getBookById(id);
        if (b == null) {
            java.util.Map<String, Object> body = new java.util.HashMap<>();
            body.put("status", 404);
            body.put("error", "Not Found");
            body.put("message", "Book not found");
            return ResponseEntity.status(404).body(body);
        }
        return ResponseEntity.ok(b);
    }

    @PostMapping
    public ResponseEntity<?> createBook(@Valid @RequestBody Book book) {
        return ResponseEntity.ok(bookService.saveBook(book));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @Valid @RequestBody Book book) {
        Book updated = bookService.updateBook(id, book);
        if (updated == null) {
            java.util.Map<String, Object> body = new java.util.HashMap<>();
            body.put("status", 404);
            body.put("error", "Not Found");
            body.put("message", "Book not found");
            return ResponseEntity.status(404).body(body);
        }
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        Book b = bookService.getBookById(id);
        if (b == null) {
            java.util.Map<String, Object> body = new java.util.HashMap<>();
            body.put("status", 404);
            body.put("error", "Not Found");
            body.put("message", "Book not found");
            return ResponseEntity.status(404).body(body);
        }
        bookService.deleteBookById(id);
        return ResponseEntity.ok().build();
    }
}
