package com.example.blog.endpoint;

import com.example.blog.domain.Comment;
import com.example.blog.model.CommentDto;
import com.example.blog.model.CreateCommentRequest;
import com.example.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentEndpoint {

    private final CommentService commentService;

    @PostMapping("/createComment")
    public ResponseEntity<CommentDto> create(@RequestBody CreateCommentRequest request) {
        var comment = commentService.create(request.getUserId(),
                request.getPostId(), request.getText());

        var commentDto = commentToCommentDto(comment);

        return ResponseEntity.ok(commentDto);
    }

    private CommentDto commentToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .postId(comment.getPost().getId())
                .userId(comment.getUser().getId())
                .text(comment.getText())
                .build();
    }
}
