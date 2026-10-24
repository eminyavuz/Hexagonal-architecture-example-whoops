package com.hexagonal_architecture_example.whoops.adapter.out.notification;

import com.hexagonal_architecture_example.whoops.application.port.out.NotificationSender;
import com.hexagonal_architecture_example.whoops.domain.SocialMediaPlatform;

import java.util.UUID;

public class ConsoleNotificationSender implements NotificationSender {
    @Override
    public void send(UUID userId, String senderName,SocialMediaPlatform sourcePlatform,String message) {
        System.out.println(
                "Whoops notification sent to " + userId +"FROM" +senderName+"On"+sourcePlatform+ ", message: " + message
        );    }

    @Override
    public SocialMediaPlatform platform() {
        return SocialMediaPlatform.X;
    }
}
