package com.polozov.bookprojectweb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "comments")
@NamedEntityGraph(name = "books-entity-graph", attributeNodes = {@NamedAttributeNode("book")})
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "comment_text")
    private String text;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Book.class)
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "comment_fk"))
    private Book book;
}