package com.hexagonal_architecture_example.whoops.domain.whoops;

import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;

import java.time.Instant;
import java.util.UUID;

public class Whoops {
    private final UUID id;
    private final UUID userId;
    private  final  String  senderName;
    private final SocialMediaPlatform sourcePlatform;
    private final String message;
    private final Instant createdAt;
    private static final int MAX_MESSAGE_LENGTH = 200;

    private Whoops(UUID id, UUID userId,String senderName,SocialMediaPlatform sourcePlatform, String message, Instant createdAt) {
        this.id = id;
        this.userId = userId;
        this.senderName=senderName;
        this.sourcePlatform=sourcePlatform;
        this.message = message;
        this.createdAt = createdAt;
    }

    public static Whoops create(UUID userId,String senderName,SocialMediaPlatform sourcePlatform, String message) {
        validateUserId(userId);
        validateMessage(message);
        validateSenderName(senderName);
        validateSourcePlatform(sourcePlatform);
        return new Whoops(UUID.randomUUID(), userId,senderName,sourcePlatform, message, Instant.now());
    }

    public String getSenderName() {
        return senderName;
    }

    public SocialMediaPlatform getSourcePlatform() {
        return sourcePlatform;
    }

    public static Whoops reconstitute(
            UUID id,
            UUID userId,
            String senderName,
            SocialMediaPlatform sourcePlatform,
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
       validateSenderName(senderName);
        validateSourcePlatform(sourcePlatform);
        validateMessage(message);
        validateUserId(userId);
        return new Whoops(
                id,
                userId,
                senderName,
                sourcePlatform,
                message,
                createdAt
        );
    }

    private static void validateSenderName(String senderName) {
        if (senderName == null || senderName.isBlank()) {
            throw new IllegalArgumentException("Sender cannot be empty");
        }
    }

    private static void validateSourcePlatform(SocialMediaPlatform sourcePlatform) {
        if (sourcePlatform == null) {
            throw new IllegalArgumentException("Source platform cannot be null");
        }
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
