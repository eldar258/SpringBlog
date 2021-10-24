package com.example.blog.service;


import com.example.blog.domain.Post;
import com.example.blog.domain.Tag;
import com.example.blog.domain.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.TagRepository;
import com.example.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final TagRepository tagRepository;

    private final UserRepository userRepository;

    public Page<Post> getUserPosts(Long userId, Pageable pageable) {
        return postRepository.findAllByUserId(userId, pageable);
    }

    public Post create(Long userId, String text, List<String> tagNames) {
        var tags = tagNames.stream()
                .map(tagName -> tagRepository.findByName(tagName)
                        .orElseGet(() -> tagRepository.saveAndFlush(new Tag(tagName))))
                .collect(Collectors.toList());


        var post = new Post();
        post.setUser(validateUserAndGet(userId));
        post.setText(text);
        post.setTags(tags);

        //tagRepository.saveAllAndFlush(tags);
        return postRepository.save(post);
    }
    private User validateUserAndGet(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public Post update(Long id, String text) {
        var post = postRepository.getById(id);

        post.setText(text);

        return postRepository.save(post);
    }


    public Page<Post> getByTag(String name, Pageable pageable) {
        return postRepository.findAllByTagName(name, pageable);
    }
}
