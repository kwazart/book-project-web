package com.polozov.bookprojectweb.repository;

import com.polozov.bookprojectweb.domain.Book;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @EntityGraph(value = "authors-genres-entity-graph")
    @Query("select b from Book b where b.name = :name")
    List<Book> findByBookName(@Param("name") String name);

    @EntityGraph(value = "authors-genres-entity-graph")
    List<Book> findAll();

    @EntityGraph(value = "authors-genres-entity-graph")
    List<Book> findAllByGenreName(String name);

    @EntityGraph(value = "authors-genres-entity-graph")
    List<Book> findAllByAuthorName(String name);
}