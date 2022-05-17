package com.polozov.bookprojectweb.repository;

import com.polozov.bookprojectweb.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query("select g from Genre g where g.name = :name")
    Genre findByName(@Param("name") String name);
}