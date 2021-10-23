package com.example.blog.service;


import com.example.blog.domain.Post;
import com.example.blog.domain.User;
import com.example.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public Page<Post> getUserPosts(Long userId, Pageable pageable) {
        var user = new User();
        user.setId(userId);
        var post = new Post();
        post.setUser(user);

        return postRepository.findAll(Example.of(post), pageable);
    }

    public Post create(Long userId, String text) {
        var post = new Post();

        var user = new User();
        user.setId(userId);
        post.setUser(user);
        post.setText(text);

        return postRepository.save(post);
    }

    public Post update(Long id, String text) {
        var post = postRepository.getById(id);

        post.setText(text);

        return postRepository.save(post);
    }
}
