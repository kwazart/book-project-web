package com.polozov.bookprojectweb.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Permission {
    GENRE_READ("genre:read"),
    GENRE_WRITE("genre:write"),
    AUTHOR_READ("author:read"),
    AUTHOR_WRITE("author:write"),
    BOOK_READ("book:read"),
    BOOK_WRITE("book:write"),
    COMMENT_READ("comment:read");

    private final String permission;
}
