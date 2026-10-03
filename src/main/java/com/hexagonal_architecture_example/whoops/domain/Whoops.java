package com.hexagonal_architecture_example.whoops.domain;

import java.time.Instant;
import java.util.UUID;

public class Whoops {
    private final UUID id;
    private final UUID userId;
    private final String message;
    private final Instant createdAt;
    private static final int MAX_MESSAGE_LENGTH = 200;

    private Whoops(UUID id, UUID userId, String message, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.createdAt = createdAt;
    }

    public static Whoops create(UUID userId, String message) {
        validateUserId(userId);
        validateMessage(message);
        return new Whoops(UUID.randomUUID(), userId, message, Instant.now());
    }

    public static Whoops reconstitute(
            UUID id,
            UUID userId,
            String message,
            Instant createdAt
    ) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (createdAt==null)
        {
            throw new IllegalArgumentException("Creation date cannot be null");
        }
        validateMessage(message);
        validateUserId(userId);
        return new Whoops(
                id,
                userId,
                message,
                createdAt
        );
    }

private  static void  validateUserId(UUID userId)
{
    if(userId==null)
    {
        throw new IllegalArgumentException("User ID cannot be null");
    }
}

private  static  void validateMessage(String message)
{
    if(message == null || message.isBlank())
    {
        throw new IllegalArgumentException("Message cannot be empty");
    }
    if(message.length()>MAX_MESSAGE_LENGTH)
    {
        throw new IllegalArgumentException("Message cannot exceed"+MAX_MESSAGE_LENGTH+" characters");
    }
}

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
