package com.hexagonal_architecture_example.whoops.adapter.out.notification;

import com.hexagonal_architecture_example.whoops.application.port.out.NotificationSender;
import com.hexagonal_architecture_example.whoops.domain.SocialMediaPlatform;

import java.util.UUID;

public class FacebookNotificationAdapter implements NotificationSender {
    @Override
    public void send(UUID userId, String senderName, SocialMediaPlatform sourcePlatform, String message) {
        System.out.println("[" + platform() + "] "+ senderName +" has sent you a message on " +sourcePlatform+" :"+message);
    }

    @Override
    public SocialMediaPlatform platform() {
        return SocialMediaPlatform.FACEBOOK;
    }
}
