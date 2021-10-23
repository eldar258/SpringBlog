package com.example.blog.service;


import com.example.blog.domain.Post;
import com.example.blog.domain.User;
import com.example.blog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

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
