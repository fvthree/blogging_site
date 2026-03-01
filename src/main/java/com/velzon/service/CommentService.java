package com.velzon.service;

import com.velzon.entity.Comment;
import com.velzon.repository.CommentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    private CommentRepository commentRepository;

    // Get approved comments for a post
    public Page<Comment> getApprovedCommentsByPost(Long postId, Pageable pageable) {
        logger.info("Fetching approved comments for post ID: {} with pagination", postId);
        Page<Comment> comments = commentRepository.findByPostIdAndIsApprovedTrue(postId, pageable);
        logger.debug("Retrieved {} approved comments for post {}", comments.getTotalElements(), postId);
        return comments;
    }

    // Get all approved comments for a post
    public List<Comment> getApprovedCommentsByPost(Long postId) {
        logger.info("Fetching all approved comments for post ID: {}", postId);
        List<Comment> comments = commentRepository.findByPostIdAndIsApprovedTrue(postId);
        logger.debug("Retrieved {} approved comments for post {}", comments.size(), postId);
        return comments;
    }

    // Get unapproved comments (for admin)
    public List<Comment> getPendingComments() {
        logger.info("Fetching pending comments for approval");
        List<Comment> comments = commentRepository.findByIsApprovedFalse();
        logger.debug("Retrieved {} pending comments", comments.size());
        return comments;
    }

    // Get comment by ID
    public Optional<Comment> getCommentById(Long id) {
        logger.debug("Fetching comment by ID: {}", id);
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            logger.debug("Found comment by ID: {}", id);
        } else {
            logger.warn("Comment not found with ID: {}", id);
        }
        return comment;
    }

    // Save comment
    public Comment saveComment(Comment comment) {
        if (comment.getId() != null) {
            logger.info("Updating comment ID: {}", comment.getId());
        } else {
            logger.info("Creating new comment for post ID: {} by {}", comment.getPost().getId(), comment.getAuthorName());
        }
        Comment savedComment = commentRepository.save(comment);
        logger.info("Comment saved successfully with ID: {}", savedComment.getId());
        return savedComment;
    }

    // Approve comment
    public Comment approveComment(Long id) {
        logger.info("Approving comment with ID: {}", id);
        Optional<Comment> comment = commentRepository.findById(id);
        if (comment.isPresent()) {
            Comment c = comment.get();
            c.setIsApproved(true);
            Comment approvedComment = commentRepository.save(c);
            logger.info("Comment approved successfully: {}", id);
            return approvedComment;
        }
        logger.warn("Could not approve comment - comment not found with ID: {}", id);
        return null;
    }

    // Delete comment
    public void deleteComment(Long id) {
        logger.info("Deleting comment with ID: {}", id);
        commentRepository.deleteById(id);
        logger.info("Comment deleted successfully: {}", id);
    }
}

