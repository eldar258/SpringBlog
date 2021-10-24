package com.example.blog.endpoint;

import com.example.blog.domain.Post;
import com.example.blog.model.CreatePostRequest;
import com.example.blog.model.PostDto;
import com.example.blog.model.UpdatePostRequest;
import com.example.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostEndpoint {
    private final PostService postService;

    @GetMapping("/getUserPosts")
    public ResponseEntity<PageImpl<PostDto>> getUserPosts(@RequestParam Long userId,
                                                 @PageableDefault(size = 5)Pageable pageable) {
        var page = postService.getUserPosts(userId, pageable);

        var pageDtos = page.stream()
                .map(this::postToPostDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new PageImpl<>(pageDtos, pageable, page.getTotalElements()));
    }

    @PostMapping("/createPost")
    public ResponseEntity<PostDto> create(@RequestBody CreatePostRequest request) {
        var post = postService.create(request.getUserId(),
                request.getText(), request.getTagNames());

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
                .tags(post.getTags())
                .build();
    }
}
