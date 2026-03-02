package com.velzon.controller;

import com.velzon.entity.Newsletter;
import com.velzon.service.NewsletterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/newsletter")
public class NewsletterAdminController {

    private static final Logger logger = LoggerFactory.getLogger(NewsletterAdminController.class);

    @Autowired
    private NewsletterService newsletterService;

    /**
     * View all newsletter subscribers
     */
    @GetMapping("")
    public String viewSubscribers(Model model) {
        logger.info("Admin accessing newsletter subscribers list");
        List<Newsletter> activeSubscribers = newsletterService.getAllActiveSubscribers();
        long totalCount = newsletterService.getActiveSubscriberCount();

        model.addAttribute("subscribers", activeSubscribers);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("pageTitle", "Newsletter Subscribers");
        logger.debug("Newsletter admin page loaded with {} active subscribers", totalCount);
        return "admin/newsletter-subscribers";
    }

    /**
     * Unsubscribe a subscriber from admin panel
     */
    @PostMapping("/{id}/unsubscribe")
    public String unsubscribeFromAdmin(@PathVariable Long id) {
        logger.info("Admin unsubscribing newsletter subscriber ID: {}", id);
        try {
            newsletterService.unsubscribeById(id);
            logger.info("Successfully unsubscribed subscriber ID: {}", id);
        } catch (Exception e) {
            logger.error("Error unsubscribing subscriber ID: {}", id, e);
        }
        return "redirect:/admin/newsletter";
    }
}



