package com.velzon.service;

import com.velzon.entity.Category;
import com.velzon.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    // Get all categories
    public List<Category> getAllCategories() {
        logger.info("Fetching all categories");
        List<Category> categories = categoryRepository.findAll();
        logger.debug("Retrieved {} categories", categories.size());
        return categories;
    }

    // Get category by ID
    public Optional<Category> getCategoryById(Long id) {
        logger.debug("Fetching category by ID: {}", id);
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            logger.debug("Found category: {}", category.get().getName());
        } else {
            logger.warn("Category not found with ID: {}", id);
        }
        return category;
    }

    // Get category by slug
    public Optional<Category> getCategoryBySlug(String slug) {
        logger.info("Fetching category by slug: {}", slug);
        Optional<Category> category = categoryRepository.findBySlug(slug);
        if (category.isPresent()) {
            logger.debug("Found category: {}", category.get().getName());
        } else {
            logger.warn("Category not found with slug: {}", slug);
        }
        return category;
    }

    // Create or update category
    public Category saveCategory(Category category) {
        if (category.getId() != null) {
            logger.info("Updating category: {} (ID: {})", category.getName(), category.getId());
        } else {
            logger.info("Creating new category: {}", category.getName());
        }
        Category savedCategory = categoryRepository.save(category);
        logger.info("Category saved successfully with ID: {}", savedCategory.getId());
        return savedCategory;
    }

    // Delete category
    public void deleteCategory(Long id) {
        logger.info("Deleting category with ID: {}", id);
        categoryRepository.deleteById(id);
        logger.info("Category deleted successfully: {}", id);
    }
}

