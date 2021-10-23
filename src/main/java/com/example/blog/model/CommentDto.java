package com.example.blog.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentDto {
    private Long id;

    private Long userId;

    private Long postId;

    private String text;
}
