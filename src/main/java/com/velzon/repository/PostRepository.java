package com.velzon.repository;

import com.velzon.entity.Post;
import com.velzon.entity.Post.PostStatus;
import com.velzon.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByStatus(PostStatus status, Pageable pageable);
    Page<Post> findByAuthorAndStatus(User author, PostStatus status, Pageable pageable);
    Page<Post> findByCategoryIdAndStatus(Long categoryId, PostStatus status, Pageable pageable);
    Optional<Post> findBySlugAndStatus(String slug, PostStatus status);
    List<Post> findByStatusOrderByPublishedAtDesc(PostStatus status);
}

