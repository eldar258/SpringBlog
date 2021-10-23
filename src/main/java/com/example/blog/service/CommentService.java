package com.example.blog.service;

import com.example.blog.domain.Comment;
import com.example.blog.domain.Post;
import com.example.blog.domain.User;
import com.example.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment create(Long userId, Long postId, String text) {
        var comment = new Comment();

        comment.setText(text);

        var user = new User();
        user.setId(userId);
        comment.setUser(user);

        var post = new Post();
        post.setId(postId);
        comment.setPost(post);

        return commentRepository.save(comment);
    }
}
