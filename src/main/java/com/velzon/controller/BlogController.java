package com.velzon.controller;

import com.velzon.entity.Category;
import com.velzon.entity.Comment;
import com.velzon.entity.Post;
import com.velzon.service.CategoryService;
import com.velzon.service.CommentService;
import com.velzon.service.PostService;
import com.velzon.service.NewsletterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class BlogController {

    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private NewsletterService newsletterService;

    // Blog home - latest posts
    @GetMapping("")
    public String index(Model model, Pageable pageable) {
        logger.info("Loading blog home page");
        Page<Post> posts = postService.getPublishedPosts(PageRequest.of(0, 10));
        List<Category> categories = categoryService.getAllCategories();
        List<Post> latestPosts = postService.getLatestPosts();

        model.addAttribute("posts", posts);
        model.addAttribute("categories", categories);
        model.addAttribute("latestPosts", latestPosts);
        model.addAttribute("pageTitle", "Blog Home");
        logger.debug("Blog home page loaded with {} posts and {} categories", posts.getTotalElements(), categories.size());
        return "blog/index";
    }

    // View single blog post
    @GetMapping("/post/{slug}")
    public String viewPost(@PathVariable String slug, Model model) {
        logger.info("Loading blog post: {}", slug);
        Optional<Post> post = postService.getPostBySlug(slug);

        if (post.isEmpty()) {
            logger.warn("Post not found with slug: {}", slug);
            return "redirect:/";
        }

        Post p = post.get();
        postService.incrementViewCount(p);

        List<Comment> comments = commentService.getApprovedCommentsByPost(p.getId());
        List<Category> categories = categoryService.getAllCategories();
        List<Post> latestPosts = postService.getLatestPosts();

        model.addAttribute("post", p);
        model.addAttribute("comments", comments);
        model.addAttribute("categories", categories);
        model.addAttribute("latestPosts", latestPosts);
        model.addAttribute("pageTitle", p.getTitle());
        logger.debug("Post loaded: {} (ID: {}) with {} comments", p.getTitle(), p.getId(), comments.size());
        return "blog/post-detail";
    }

    // View posts by category
    @GetMapping("/category/{slug}")
    public String viewCategory(@PathVariable String slug, Model model, Pageable pageable) {
        logger.info("Loading category page: {}", slug);
        Optional<Category> category = categoryService.getCategoryBySlug(slug);

        if (category.isEmpty()) {
            logger.warn("Category not found with slug: {}", slug);
            return "redirect:/";
        }

        Page<Post> posts = postService.getPostsByCategory(category.get().getId(), PageRequest.of(0, 10));
        List<Category> categories = categoryService.getAllCategories();
        List<Post> latestPosts = postService.getLatestPosts();

        model.addAttribute("posts", posts);
        model.addAttribute("category", category.get());
        model.addAttribute("categories", categories);
        model.addAttribute("latestPosts", latestPosts);
        model.addAttribute("pageTitle", "Category: " + category.get().getName());
        logger.debug("Category page loaded: {} with {} posts", category.get().getName(), posts.getTotalElements());
        return "blog/category-posts";
    }

    // Add comment to post
    @PostMapping("/post/{id}/comment")
    public String addComment(@PathVariable Long id,
                           @RequestParam String authorName,
                           @RequestParam String authorEmail,
                           @RequestParam String content,
                           Model model) {
        logger.info("Adding comment to post ID: {} by {}", id, authorName);
        Optional<Post> post = postService.getPostById(id);

        if (post.isEmpty()) {
            logger.warn("Cannot add comment - post not found with ID: {}", id);
            return "redirect:/";
        }

        Comment comment = new Comment();
        comment.setPost(post.get());
        comment.setAuthorName(authorName);
        comment.setAuthorEmail(authorEmail);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setIsApproved(false); // Comments require approval

        Comment savedComment = commentService.saveComment(comment);
        logger.info("Comment added successfully (ID: {}) to post: {} - Awaiting approval", savedComment.getId(), post.get().getTitle());

        return "redirect:/post/" + post.get().getSlug();
    }

    // Search posts
    @GetMapping("/search")
    public String search(@RequestParam(required = false) String q, Model model) {
        logger.info("Performing search with query: {}", q != null ? q : "empty");
        // Simple search - you can enhance this later
        List<Category> categories = categoryService.getAllCategories();
        List<Post> latestPosts = postService.getLatestPosts();

        model.addAttribute("categories", categories);
        model.addAttribute("latestPosts", latestPosts);
        model.addAttribute("searchQuery", q != null ? q : "");
        model.addAttribute("pageTitle", "Search Results");
        logger.debug("Search results page loaded for query: {}", q);
        return "blog/search-results";
    }

    // Newsletter subscription
    @PostMapping("/newsletter/subscribe")
    public String subscribeNewsletter(@RequestParam String email,
                                     @RequestParam(required = false) String from,
                                     Model model) {
        logger.info("Newsletter subscription request for email: {}", email);

        try {
            newsletterService.subscribe(email);
            logger.info("Successfully subscribed email: {}", email);

            // Redirect back to the referrer or home page
            String redirectUrl = (from != null && !from.isEmpty()) ? from : "/";
            return "redirect:" + redirectUrl + "?subscribed=true";
        } catch (Exception e) {
            logger.error("Error subscribing email: {}", email, e);
            String redirectUrl = (from != null && !from.isEmpty()) ? from : "/";
            return "redirect:" + redirectUrl + "?subscribed=false";
        }
    }

    // Newsletter unsubscribe
    @PostMapping("/newsletter/unsubscribe")
    public String unsubscribeNewsletter(@RequestParam String email, Model model) {
        logger.info("Newsletter unsubscription request for email: {}", email);

        try {
            newsletterService.unsubscribe(email);
            logger.info("Successfully unsubscribed email: {}", email);
            return "redirect:/?unsubscribed=true";
        } catch (Exception e) {
            logger.error("Error unsubscribing email: {}", email, e);
            return "redirect:/?unsubscribed=false";
        }
    }
}

