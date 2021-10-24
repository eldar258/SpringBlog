package com.example.blog.service;


import com.example.blog.domain.Post;
import com.example.blog.domain.Tag;
import com.example.blog.domain.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    private final TagRepository tagRepository;

    public Page<Post> getUserPosts(Long userId, Pageable pageable) {
        var user = new User();
        user.setId(userId);
        var post = new Post();
        post.setUser(user);

        return postRepository.findAll(Example.of(post), pageable);
    }

    public Post create(Long userId, String text, List<String> tagNames) {
        var tags = getTagsWithName(tagNames);//tagNames != null ? getTagsWithName(tagNames) : List.of();

        var post = new Post();
        var user = new User();
        user.setId(userId);
        post.setUser(user);
        post.setText(text);

        post.setTags(tags);

        tags.forEach(tag -> tag.addPost(post));

        tagRepository.saveAllAndFlush(tags);
        return postRepository.save(post);
    }
    private List<Tag> getTagsWithName(List<String> tagNames) {
        return tagNames.stream()
                .map(tagName -> findTagByName(getTagWithName(tagName)))
                .collect(Collectors.toList());
    }
    private Tag findTagByName(Tag tag) {
        return tagRepository.findOne(Example.of(tag)).orElse(tag);
    }
    private boolean isTagAbsent(Tag tag) {
        return tagRepository.findOne(Example.of(tag)).isEmpty();
    }
    private Tag getTagWithName(String tagName) {
        var tag = new Tag();
        tag.setName(tagName);
        return tag;
    }

    public Post update(Long id, String text) {
        var post = postRepository.getById(id);

        post.setText(text);

        return postRepository.save(post);
    }
}
