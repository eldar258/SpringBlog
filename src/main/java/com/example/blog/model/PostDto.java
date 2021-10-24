package com.example.blog.model;

import com.example.blog.domain.Tag;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PostDto {
    private Long postId;

    private Long userId;

    private String text;

    private List<TagDto> tags;
}
