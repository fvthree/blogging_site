package com.velzon.service;

import com.velzon.entity.Post;
import com.velzon.entity.Post.PostStatus;
import com.velzon.entity.User;
import com.velzon.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    private PostRepository postRepository;

    // Get all published posts with pagination
    public Page<Post> getPublishedPosts(Pageable pageable) {
        logger.info("Fetching published posts with pagination: {}", pageable);
        Page<Post> posts = postRepository.findByStatus(PostStatus.PUBLISHED, pageable);
        logger.debug("Retrieved {} published posts", posts.getTotalElements());
        return posts;
    }

    // Get published posts by category
    public Page<Post> getPostsByCategory(Long categoryId, Pageable pageable) {
        logger.info("Fetching published posts for category ID: {}", categoryId);
        Page<Post> posts = postRepository.findByCategoryIdAndStatus(categoryId, PostStatus.PUBLISHED, pageable);
        logger.debug("Retrieved {} posts for category {}", posts.getTotalElements(), categoryId);
        return posts;
    }

    // Get published posts by author
    public Page<Post> getPostsByAuthor(User author, Pageable pageable) {
        logger.info("Fetching published posts by author: {}", author.getUsername());
        Page<Post> posts = postRepository.findByAuthorAndStatus(author, PostStatus.PUBLISHED, pageable);
        logger.debug("Retrieved {} posts by author {}", posts.getTotalElements(), author.getUsername());
        return posts;
    }

    // Get post by slug
    public Optional<Post> getPostBySlug(String slug) {
        logger.info("Fetching post by slug: {}", slug);
        Optional<Post> post = postRepository.findBySlugAndStatus(slug, PostStatus.PUBLISHED);
        if (post.isPresent()) {
            logger.debug("Found post: {} (ID: {})", post.get().getTitle(), post.get().getId());
        } else {
            logger.warn("Post not found with slug: {}", slug);
        }
        return post;
    }

    // Get post by ID
    public Optional<Post> getPostById(Long id) {
        logger.debug("Fetching post by ID: {}", id);
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            logger.debug("Found post: {} (ID: {})", post.get().getTitle(), id);
        } else {
            logger.warn("Post not found with ID: {}", id);
        }
        return post;
    }

    // Create or update post
    public Post savePost(Post post) {
        if (post.getId() != null) {
            logger.info("Updating post: {} (ID: {})", post.getTitle(), post.getId());
        } else {
            logger.info("Creating new post: {}", post.getTitle());
        }
        Post savedPost = postRepository.save(post);
        logger.info("Post saved successfully with ID: {}", savedPost.getId());
        return savedPost;
    }

    // Delete post
    public void deletePost(Long id) {
        logger.info("Deleting post with ID: {}", id);
        postRepository.deleteById(id);
        logger.info("Post deleted successfully: {}", id);
    }

    // Publish post
    public Post publishPost(Long id) {
        logger.info("Publishing post with ID: {}", id);
        Optional<Post> post = postRepository.findById(id);
        if (post.isPresent()) {
            Post p = post.get();
            p.setStatus(PostStatus.PUBLISHED);
            p.setPublishedAt(LocalDateTime.now());
            Post publishedPost = postRepository.save(p);
            logger.info("Post published successfully: {} (ID: {})", p.getTitle(), id);
            return publishedPost;
        }
        logger.warn("Could not publish post - post not found with ID: {}", id);
        return null;
    }

    // Get latest published posts
    public List<Post> getLatestPosts() {
        logger.info("Fetching latest published posts");
        List<Post> posts = postRepository.findByStatusOrderByPublishedAtDesc(PostStatus.PUBLISHED);
        logger.debug("Retrieved {} latest posts", posts.size());
        return posts;
    }

    // Increment view count
    public void incrementViewCount(Post post) {
        long newViewCount = post.getViewCount() + 1;
        post.setViewCount(newViewCount);
        postRepository.save(post);
        logger.debug("Post view count incremented: {} (ID: {}) - New count: {}", post.getTitle(), post.getId(), newViewCount);
    }
}

