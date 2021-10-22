package com.example.blog.model;

import lombok.Getter;

@Getter
public class CreateUserRequest {

    private String login;

    private String email;

    private String password;
}
