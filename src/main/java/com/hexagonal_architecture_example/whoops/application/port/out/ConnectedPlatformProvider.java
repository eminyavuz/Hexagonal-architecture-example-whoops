package com.hexagonal_architecture_example.whoops.application.port.out;

import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;


import java.util.Set;
import java.util.UUID;

public interface ConnectedPlatformProvider {
     Set<SocialMediaPlatform> getConnectedPlatforms(UUID userId);
}
