package com.bibliotheque.gestion_bibliotheque.service;

import com.bibliotheque.gestion_bibliotheque.domain.Category;
import com.bibliotheque.gestion_bibliotheque.repository.AuthorRepository;
import com.bibliotheque.gestion_bibliotheque.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public Map<Category, Long> booksPerCategory() {
        Map<Category, Long> result = new LinkedHashMap<>();
        for (Category c : Category.values()) {
            long count = bookRepository.count((root, query, cb) -> cb.equal(root.get("category"), c));
            result.put(c, count);
        }
        return result;
    }

    public List<AuthorRepository.TopAuthorProjection> topAuthors(int limit) {
        return authorRepository.findTopAuthors(PageRequest.of(0, limit));
    }
}
