package com.example.blog.model;

import lombok.Getter;

@Getter
public class CreateCommentRequest {

    private Long userId;
    private Long postId;
    private String text;
}
