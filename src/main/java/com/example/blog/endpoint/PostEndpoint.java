package com.example.blog.endpoint;

import com.example.blog.domain.Post;
import com.example.blog.domain.Tag;
import com.example.blog.model.CreatePostRequest;
import com.example.blog.model.PostDto;
import com.example.blog.model.TagDto;
import com.example.blog.model.UpdatePostRequest;
import com.example.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostEndpoint {
    private final PostService postService;

    @GetMapping("/getByTag")
    public ResponseEntity<PageImpl<PostDto>> getByTag(@RequestParam String name,
                                                  @PageableDefault(size = 5) Pageable pageable) {
        var page = postService.getByTag(name, pageable);

        var pageDtos = postsPageToPostDtos(page);

        return ResponseEntity.ok(new PageImpl<>(pageDtos, pageable, page.getTotalElements()));
    }

    @GetMapping("/getUserPosts")
    public ResponseEntity<PageImpl<PostDto>> getUserPosts(@RequestParam Long userId,
                                                 @PageableDefault(size = 5)Pageable pageable) {
        var page = postService.getUserPosts(userId, pageable);

        var pageDtos = postsPageToPostDtos(page);

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

    private List<PostDto> postsPageToPostDtos(Page<Post> page) {
        return page.stream()
                .map(this::postToPostDto)
                .collect(Collectors.toList());
    }

    private PostDto postToPostDto(Post post) {
        return PostDto.builder()
                .postId(post.getId())
                .text(post.getText())
                .userId(post.getUser().getId())
                .tags(tagsToTagsDto(post.getTags()))
                .build();
    }

    private List<TagDto> tagsToTagsDto(List<Tag> tags) {
        return tags.stream()
                .map(this::tagToTagDto)
                .collect(Collectors.toList());
    }

    private TagDto tagToTagDto(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}
