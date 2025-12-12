package com.bibliotheque.gestion_bibliotheque.service;

import com.bibliotheque.gestion_bibliotheque.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bibliotheque.gestion_bibliotheque.repository.BookRepository;
import com.bibliotheque.gestion_bibliotheque.repository.BookSpecifications;
import com.bibliotheque.gestion_bibliotheque.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        Optional<Book> b = bookRepository.findById(id);
        return b.orElse(null);
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    public Book updateBook(Long id, Book book) {
        Book existing = bookRepository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setTitle(book.getTitle());
        existing.setIsbn(book.getIsbn());
        existing.setYear(book.getYear());
        existing.setCategory(book.getCategory());
        existing.setAuthor(book.getAuthor());
        return bookRepository.save(existing);
    }

    public Page<Book> searchBooks(String title, Long authorId, Category category, Integer yearFrom, Integer yearTo, Pageable pageable) {
        BookSpecifications.BookQuery q = new BookSpecifications.BookQuery();
        q.title = title;
        q.authorId = authorId;
        q.category = category;
        q.yearFrom = yearFrom;
        q.yearTo = yearTo;
        Specification<Book> spec = BookSpecifications.all(q);
        return bookRepository.findAll(spec, pageable);
    }
}
