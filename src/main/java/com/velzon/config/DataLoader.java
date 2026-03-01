package com.velzon.config;

import com.velzon.entity.Category;
import com.velzon.entity.Post;
import com.velzon.entity.User;
import com.velzon.repository.CategoryRepository;
import com.velzon.repository.PostRepository;
import com.velzon.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public void run(String... args) throws Exception {
        // Check if data already exists
        long userCount = userRepository.count();
        if (userCount > 0) {
            logger.info("Database already contains data. Skipping initial data loading.");
            return;
        }

        logger.info("========================================");
        logger.info("Starting initial blog data loading...");
        logger.info("========================================");

        // Create sample users
        User author1 = new User();
        author1.setUsername("john_doe");
        author1.setEmail("john@example.com");
        author1.setPassword("password123");
        author1.setFirstName("John");
        author1.setLastName("Doe");
        author1.setBio("Tech enthusiast and blogger");
        author1.setIsAuthor(true);
        author1.setIsActive(true);
        userRepository.save(author1);
        logger.info("Created user: {}", author1.getUsername());

        User author2 = new User();
        author2.setUsername("jane_smith");
        author2.setEmail("jane@example.com");
        author2.setPassword("password123");
        author2.setFirstName("Jane");
        author2.setLastName("Smith");
        author2.setBio("Lifestyle blogger");
        author2.setIsAuthor(true);
        author2.setIsActive(true);
        userRepository.save(author2);
        logger.info("Created user: {}", author2.getUsername());

        // Create sample categories
        Category tech = new Category();
        tech.setName("Technology");
        tech.setSlug("technology");
        tech.setDescription("Articles about technology, programming, and software development");
        categoryRepository.save(tech);
        logger.info("Created category: {}", tech.getName());

        Category lifestyle = new Category();
        lifestyle.setName("Lifestyle");
        lifestyle.setSlug("lifestyle");
        lifestyle.setDescription("Life tips and personal development");
        categoryRepository.save(lifestyle);
        logger.info("Created category: {}", lifestyle.getName());

        Category tutorials = new Category();
        tutorials.setName("Tutorials");
        tutorials.setSlug("tutorials");
        tutorials.setDescription("Step-by-step guides and tutorials");
        categoryRepository.save(tutorials);
        logger.info("Created category: {}", tutorials.getName());

        // Create sample posts
        Post post1 = new Post();
        post1.setTitle("Getting Started with Spring Boot");
        post1.setSlug("getting-started-with-spring-boot");
        post1.setContent("<h2>Introduction</h2><p>Spring Boot is an excellent framework for building Java applications...</p>" +
                "<h2>Features</h2><ul><li>Auto-configuration</li><li>Embedded server</li><li>Easy dependency management</li></ul>");
        post1.setExcerpt("Learn the basics of Spring Boot and start building robust Java applications.");
        post1.setAuthor(author1);
        post1.setCategory(tech);
        post1.setStatus(Post.PostStatus.PUBLISHED);
        post1.setPublishedAt(LocalDateTime.now().minusDays(5));
        post1.setViewCount(245L);
        postRepository.save(post1);
        logger.info("Created post: {}", post1.getTitle());

        Post post2 = new Post();
        post2.setTitle("10 Productivity Tips for Developers");
        post2.setSlug("10-productivity-tips-for-developers");
        post2.setContent("<h2>Introduction</h2><p>Productivity is key to being a successful developer...</p>" +
                "<h2>Tips</h2><ol><li>Use a good IDE</li><li>Take regular breaks</li><li>Stay organized</li></ol>");
        post2.setExcerpt("Discover practical tips to boost your productivity as a software developer.");
        post2.setAuthor(author1);
        post2.setCategory(lifestyle);
        post2.setStatus(Post.PostStatus.PUBLISHED);
        post2.setPublishedAt(LocalDateTime.now().minusDays(3));
        post2.setViewCount(156L);
        postRepository.save(post2);
        logger.info("Created post: {}", post2.getTitle());

        Post post3 = new Post();
        post3.setTitle("Building a Blog with Spring Boot and Thymeleaf");
        post3.setSlug("building-a-blog-with-spring-boot-and-thymeleaf");
        post3.setContent("<h2>Overview</h2><p>In this tutorial, we'll build a complete blog application...</p>" +
                "<h2>Prerequisites</h2><ul><li>Java 11+</li><li>Maven</li><li>Basic Spring Boot knowledge</li></ul>");
        post3.setExcerpt("A complete guide to building a modern blog platform using Spring Boot and Thymeleaf.");
        post3.setAuthor(author2);
        post3.setCategory(tutorials);
        post3.setStatus(Post.PostStatus.PUBLISHED);
        post3.setPublishedAt(LocalDateTime.now().minusDays(1));
        post3.setViewCount(342L);
        postRepository.save(post3);
        logger.info("Created post: {}", post3.getTitle());

        Post post4 = new Post();
        post4.setTitle("The Future of Java");
        post4.setSlug("the-future-of-java");
        post4.setContent("<h2>Java 21 Features</h2><p>Java continues to evolve with new features...</p>" +
                "<h2>What's New</h2><ul><li>Virtual threads</li><li>Pattern matching</li><li>Record classes</li></ul>");
        post4.setExcerpt("Explore the latest features and future direction of the Java programming language.");
        post4.setAuthor(author1);
        post4.setCategory(tech);
        post4.setStatus(Post.PostStatus.PUBLISHED);
        post4.setPublishedAt(LocalDateTime.now().minusDays(7));
        post4.setViewCount(512L);
        postRepository.save(post4);
        logger.info("Created post: {}", post4.getTitle());

        Post post5 = new Post();
        post5.setTitle("Work-Life Balance Tips");
        post5.setSlug("work-life-balance-tips");
        post5.setContent("<p>Maintaining a healthy work-life balance is essential for your well-being...</p>");
        post5.setExcerpt("Practical advice for maintaining a healthy balance between work and personal life.");
        post5.setAuthor(author2);
        post5.setCategory(lifestyle);
        post5.setStatus(Post.PostStatus.PUBLISHED);
        post5.setPublishedAt(LocalDateTime.now().minusDays(10));
        post5.setViewCount(287L);
        postRepository.save(post5);
        logger.info("Created post: {}", post5.getTitle());

        logger.info("========================================");
        logger.info("✅ Sample blog data loaded successfully!");
        logger.info("========================================");
    }
}

