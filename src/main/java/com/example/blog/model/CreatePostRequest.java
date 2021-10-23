package com.example.blog.model;

import com.example.blog.domain.User;
import lombok.Getter;

@Getter
public class CreatePostRequest {
    private Long userId;

    private String text;
}
