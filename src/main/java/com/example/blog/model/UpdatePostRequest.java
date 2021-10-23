package com.example.blog.model;

import lombok.Getter;

@Getter
public class UpdatePostRequest {
    private Long id;

    private String text;
}
