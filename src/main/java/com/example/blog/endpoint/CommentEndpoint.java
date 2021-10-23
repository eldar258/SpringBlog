package com.example.blog.endpoint;

import com.example.blog.domain.Comment;
import com.example.blog.model.CommentDto;
import com.example.blog.model.CreateCommentRequest;
import com.example.blog.model.UpdatePostRequest;
import com.example.blog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentEndpoint {

    private final CommentService commentService;

    @GetMapping("/getCommentsByPostId")
    public ResponseEntity<PageImpl<CommentDto>> getCommentsByPostId(Long postId,
                           @PageableDefault(size = 5)Pageable pageable) {
        var page = commentService.getCommentsByPostId(postId, pageable);

        var commentDtos = page.stream()
                .map(this::commentToCommentDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageImpl<>(commentDtos, pageable, pageable.getPageNumber()));
    }

    @PostMapping("/updateComment")
    public ResponseEntity<CommentDto> update(@RequestBody UpdatePostRequest request) {
        var comment = commentService.update(request.getId(), request.getText());

        var commentDto = commentToCommentDto(comment);

        return ResponseEntity.ok(commentDto);
    }

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
