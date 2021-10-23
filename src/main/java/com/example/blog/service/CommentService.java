package com.example.blog.service;

import com.example.blog.domain.Comment;
import com.example.blog.domain.Post;
import com.example.blog.domain.User;
import com.example.blog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Comment update(Long id, String text) {
        var comment = commentRepository.getById(id);

        comment.setText(text);

        return commentRepository.save(comment);
    }

    public Page<Comment> getCommentsByPostId(Long postId, Pageable pageable) {
        var post = new Post();
        post.setId(postId);

        var comment = new Comment();
        comment.setPost(post);

        return commentRepository.findAll(Example.of(comment), pageable);
    }
}
