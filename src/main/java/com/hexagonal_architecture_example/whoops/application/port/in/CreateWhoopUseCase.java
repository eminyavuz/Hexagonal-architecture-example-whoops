package com.hexagonal_architecture_example.whoops.application.port.in;

import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;
import com.hexagonal_architecture_example.whoops.domain.whoops.Whoops;

import java.util.UUID;

public interface CreateWhoopUseCase {
    Whoops create(UUID userId, String senderName, SocialMediaPlatform sourcePlatform, String message);
}
