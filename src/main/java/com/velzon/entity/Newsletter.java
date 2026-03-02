package com.velzon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "newsletter_subscribers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Newsletter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "subscribed_at")
    private LocalDateTime subscribedAt = LocalDateTime.now();

    @Column(name = "is_active")
    private Boolean isActive = true;

    public Newsletter(String email) {
        this.email = email;
        this.subscribedAt = LocalDateTime.now();
        this.isActive = true;
    }
}

