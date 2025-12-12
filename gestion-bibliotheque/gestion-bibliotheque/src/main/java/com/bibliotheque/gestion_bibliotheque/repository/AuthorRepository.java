package com.bibliotheque.gestion_bibliotheque.repository;

import com.bibliotheque.gestion_bibliotheque.domain.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.domain.Pageable;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, Long> {
    @Query("SELECT a.id as id, a.firstName as firstName, a.lastName as lastName, COUNT(b) as booksCount " +
            "FROM Author a LEFT JOIN a.books b " +
            "GROUP BY a.id, a.firstName, a.lastName " +
            "ORDER BY COUNT(b) DESC")
    List<TopAuthorProjection> findTopAuthors(Pageable pageable);

    interface TopAuthorProjection {
        Long getId();
        String getFirstName();
        String getLastName();
        Long getBooksCount();
    }
}
