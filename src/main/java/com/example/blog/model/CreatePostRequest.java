package com.example.blog.model;

import lombok.Getter;

import java.util.List;

@Getter
public class CreatePostRequest {
    private Long userId;

    private String text;

    private List<String> tagNames;
}
