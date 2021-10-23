package com.example.blog.endpoint;

import com.example.blog.model.CreatePostRequest;
import com.example.blog.model.PostDto;
import com.example.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostEndpoint {
    private final PostService postService;

    @PostMapping("/createPost")
    public ResponseEntity<PostDto> create(@RequestBody CreatePostRequest request) {
        var post = postService.create(request.getUserId(), request.getText());

        var postDto = PostDto.builder()
                .postId(post.getId())
                .text(post.getText())
                .userId(post.getUser().getId())
                .build();

        return ResponseEntity.ok(postDto);
    }
}
