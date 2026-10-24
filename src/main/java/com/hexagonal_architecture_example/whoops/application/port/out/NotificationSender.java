package com.hexagonal_architecture_example.whoops.application.port.out;

import com.hexagonal_architecture_example.whoops.domain.SocialMediaPlatform;

import java.util.UUID;

public interface NotificationSender {
    void send(UUID userId,String senderName,SocialMediaPlatform sourcePlatform , String message);
    SocialMediaPlatform platform();
}
