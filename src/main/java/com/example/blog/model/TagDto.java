package com.example.blog.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TagDto {

    private Long id;

    private String name;
}
