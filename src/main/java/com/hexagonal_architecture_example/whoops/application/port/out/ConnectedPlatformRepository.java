package com.hexagonal_architecture_example.whoops.application.port.out;

import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;

import java.util.UUID;

public interface ConnectedPlatformRepository {
    void save(UUID userId, SocialMediaPlatform platform);

    boolean exists(UUID userId, SocialMediaPlatform platform);
}
