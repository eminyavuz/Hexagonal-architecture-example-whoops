package com.hexagonal_architecture_example.whoops.domain;

import java.time.Instant;
import java.util.UUID;

public record WhoopsCreatedEvent(
        UUID whoopsId,
        UUID userId,
        String message,
        Instant occurredAt)
{


}
