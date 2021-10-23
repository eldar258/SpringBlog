package com.example.blog.endpoint;

import com.example.blog.domain.Post;
import com.example.blog.model.CreatePostRequest;
import com.example.blog.model.PostDto;
import com.example.blog.model.UpdatePostRequest;
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

        var postDto = postToPostDto(post);

        return ResponseEntity.ok(postDto);
    }

    @PostMapping("/updatePost")
    public ResponseEntity<PostDto> update(@RequestBody UpdatePostRequest request) {
        var post = postService.update(request.getId(), request.getText());

        var postDto = postToPostDto(post);

        return ResponseEntity.ok(postDto);
    }

    private PostDto postToPostDto(Post post) {
        return PostDto.builder()
                .postId(post.getId())
                .text(post.getText())
                .userId(post.getUser().getId())
                .build();
    }
}
