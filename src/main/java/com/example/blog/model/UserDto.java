package com.example.blog.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDto {

    private Long id;

    private String login;

    private String email;
}
