package com.hexagonal_architecture_example.whoops.domain.user;

import com.hexagonal_architecture_example.whoops.adapter.out.notification.InstagramNotificationAdapter;

import java.time.Instant;
import java.util.UUID;

public class User {
    private final UUID id;
    private final String userName;
    private final Instant createdAt;

    public User(UUID id, String userName,Instant createdAt )
    {
        if (id == null)
        {
        throw new IllegalArgumentException("User ID cannot be null");
    }

        if (userName == null || userName.isBlank())
        {
            throw new IllegalArgumentException("Username cannot be blank");
        }

        if (createdAt == null)
        {
            throw new IllegalArgumentException("CreatedAt cannot be null");
        }
        this.id=id;
        this.userName= userName.trim();
        this.createdAt=createdAt;
    }

    public static User create(String userName)
    {
        return  new User(UUID.randomUUID(),userName, Instant.now());
    }


    public UUID getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
