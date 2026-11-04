package com.hexagonal_architecture_example.whoops.domain.whoops;

import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;

import java.time.Instant;
import java.util.UUID;

public record WhoopsCreatedEvent(
        UUID whoopsId,
        UUID userId,
        String senderName,
        SocialMediaPlatform sourcePlatform,
        String message,
        Instant occurredAt)
{


}
