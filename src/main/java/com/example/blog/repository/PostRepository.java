package com.example.blog.repository;

import com.example.blog.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByUserId(Long userId, Pageable pageable);

    @Query("""
            SELECT post FROM Post post
            INNER JOIN post.tags tag
            WHERE tag.name = ?1
            """)
    Page<Post> findAllByTagName(String tagName, Pageable pageable);
}
