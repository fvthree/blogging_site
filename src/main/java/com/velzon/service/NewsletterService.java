package com.velzon.service;

import com.velzon.entity.Newsletter;
import com.velzon.repository.NewsletterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsletterService {
    private static final Logger logger = LoggerFactory.getLogger(NewsletterService.class);

    @Autowired
    private NewsletterRepository newsletterRepository;

    /**
     * Subscribe a new email to the newsletter
     * @param email The email address to subscribe
     * @return The saved Newsletter object
     */
    public Newsletter subscribe(String email) {
        logger.info("Processing newsletter subscription for email: {}", email);

        // Check if email already exists
        Optional<Newsletter> existingSubscriber = newsletterRepository.findByEmail(email);

        if (existingSubscriber.isPresent()) {
            Newsletter subscriber = existingSubscriber.get();
            if (subscriber.getIsActive()) {
                logger.warn("Email already subscribed: {}", email);
                return subscriber;
            } else {
                // Reactivate if was previously unsubscribed
                subscriber.setIsActive(true);
                subscriber.setSubscribedAt(LocalDateTime.now());
                logger.info("Reactivating subscription for email: {}", email);
                return newsletterRepository.save(subscriber);
            }
        }

        // Create new subscriber
        Newsletter newsletter = new Newsletter(email);
        Newsletter savedNewsletter = newsletterRepository.save(newsletter);
        logger.info("New newsletter subscription added: {} (ID: {})", email, savedNewsletter.getId());

        return savedNewsletter;
    }

    /**
     * Unsubscribe an email from the newsletter
     * @param email The email address to unsubscribe
     */
    public void unsubscribe(String email) {
        logger.info("Processing newsletter unsubscription for email: {}", email);
        Optional<Newsletter> subscriber = newsletterRepository.findByEmail(email);

        if (subscriber.isPresent()) {
            subscriber.get().setIsActive(false);
            newsletterRepository.save(subscriber.get());
            logger.info("Email unsubscribed: {}", email);
        } else {
            logger.warn("Unsubscribe attempted for non-existent email: {}", email);
        }
    }

    /**
     * Unsubscribe a subscriber by ID
     * @param id The subscriber ID to unsubscribe
     */
    public void unsubscribeById(Long id) {
        logger.info("Processing newsletter unsubscription for ID: {}", id);
        Optional<Newsletter> subscriber = newsletterRepository.findById(id);

        if (subscriber.isPresent()) {
            subscriber.get().setIsActive(false);
            newsletterRepository.save(subscriber.get());
            logger.info("Subscriber ID unsubscribed: {}", id);
        } else {
            logger.warn("Unsubscribe attempted for non-existent ID: {}", id);
        }
    }

    /**
     * Get all active subscribers
     * @return List of active Newsletter objects
     */
    public List<Newsletter> getAllActiveSubscribers() {
        logger.debug("Fetching all active subscribers");
        return newsletterRepository.findAll().stream()
                .filter(Newsletter::getIsActive)
                .collect(Collectors.toList());
    }

    /**
     * Get total count of active subscribers
     * @return Count of active subscribers
     */
    public long getActiveSubscriberCount() {
        return newsletterRepository.countByIsActiveTrue();
    }

    /**
     * Check if an email is subscribed
     * @param email The email to check
     * @return true if subscribed and active, false otherwise
     */
    public boolean isSubscribed(String email) {
        Optional<Newsletter> subscriber = newsletterRepository.findByEmail(email);
        return subscriber.isPresent() && subscriber.get().getIsActive();
    }

    /**
     * Get subscriber by email
     * @param email The email address
     * @return Optional containing the Newsletter if found
     */
    public Optional<Newsletter> getSubscriberByEmail(String email) {
        return newsletterRepository.findByEmail(email);
    }
}


