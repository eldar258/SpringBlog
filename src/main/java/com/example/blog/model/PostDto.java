package com.example.blog.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostDto {
    private Long postId;

    private Long userId;

    private String text;
}
