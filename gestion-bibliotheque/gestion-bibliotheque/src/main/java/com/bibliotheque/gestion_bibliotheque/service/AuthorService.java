package com.bibliotheque.gestion_bibliotheque.service;

import com.bibliotheque.gestion_bibliotheque.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bibliotheque.gestion_bibliotheque.repository.AuthorRepository;

import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    public Iterable<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public Author getAuthorById(Long id) {
        Optional<Author> a = authorRepository.findById(id);
        return a.orElse(null);
    }

    public void deleteAuthorById(Long id) {
        authorRepository.deleteById(id);
    }

    public Author updateAuthor(Long id, Author author) {
        Author existing = authorRepository.findById(id).orElse(null);
        if (existing == null) return null;
        existing.setFirstName(author.getFirstName());
        existing.setLastName(author.getLastName());
        existing.setBirthYear(author.getBirthYear());
        return authorRepository.save(existing);
    }
}
