package com.bibliotheque.gestion_bibliotheque.repository;

import com.bibliotheque.gestion_bibliotheque.domain.Book;
import com.bibliotheque.gestion_bibliotheque.domain.Category;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecifications {
    public static Specification<Book> titleContains(String title) {
        if (title == null || title.isBlank()) return null;
        return (root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Book> hasAuthorId(Long authorId) {
        if (authorId == null) return null;
        return (root, query, cb) -> cb.equal(root.get("author").get("id"), authorId);
    }

    public static Specification<Book> hasCategory(Category category) {
        if (category == null) return null;
        return (root, query, cb) -> cb.equal(root.get("category"), category);
    }

    public static Specification<Book> yearBetween(Integer from, Integer to) {
        if (from == null && to == null) return null;
        if (from != null && to != null) {
            return (root, query, cb) -> cb.between(root.get("year"), from, to);
        } else if (from != null) {
            return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("year"), from);
        } else {
            return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("year"), to);
        }
    }

    public static Specification<Book> all(BookQuery q) {
        Specification<Book> spec = Specification.where(null);
        if (q == null) return spec;
        Specification<Book> s1 = titleContains(q.title);
        Specification<Book> s2 = hasAuthorId(q.authorId);
        Specification<Book> s3 = hasCategory(q.category);
        Specification<Book> s4 = yearBetween(q.yearFrom, q.yearTo);
        if (s1 != null) spec = spec.and(s1);
        if (s2 != null) spec = spec.and(s2);
        if (s3 != null) spec = spec.and(s3);
        if (s4 != null) spec = spec.and(s4);
        return spec;
    }

    public static class BookQuery {
        public String title;
        public Long authorId;
        public Category category;
        public Integer yearFrom;
        public Integer yearTo;
    }
}
