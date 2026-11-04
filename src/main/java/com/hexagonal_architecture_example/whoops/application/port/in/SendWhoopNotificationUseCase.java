package com.hexagonal_architecture_example.whoops.application.port.in;

import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;

import java.util.UUID;

public interface SendWhoopNotificationUseCase {
    void send(UUID id, String senderName, SocialMediaPlatform sourcePlatfrom, String message);
}
