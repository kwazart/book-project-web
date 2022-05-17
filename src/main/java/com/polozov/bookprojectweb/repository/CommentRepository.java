package com.polozov.bookprojectweb.repository;

import com.polozov.bookprojectweb.domain.Book;
import com.polozov.bookprojectweb.domain.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(value = "books-entity-graph")
    @Query("select c from Comment c where c.book = :book")
    List<Comment> findByBook(@Param("book") Book book);
}