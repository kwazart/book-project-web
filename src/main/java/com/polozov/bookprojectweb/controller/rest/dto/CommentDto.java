package com.polozov.bookprojectweb.controller.rest.dto;

import com.polozov.bookprojectweb.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentDto {

    private long id;
    private String text;

    public static CommentDto toDto(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText());
    }
}
