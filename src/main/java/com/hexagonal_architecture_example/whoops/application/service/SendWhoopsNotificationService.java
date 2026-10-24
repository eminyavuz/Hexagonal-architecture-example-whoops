package com.hexagonal_architecture_example.whoops.application.service;

import com.hexagonal_architecture_example.whoops.application.port.in.SendWhoopNotificationUseCase;
import com.hexagonal_architecture_example.whoops.application.port.out.ConnectedPlatformProvider;
import com.hexagonal_architecture_example.whoops.application.port.out.NotificationSender;
import com.hexagonal_architecture_example.whoops.domain.SocialMediaPlatform;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class SendWhoopsNotificationService implements SendWhoopNotificationUseCase , ConnectedPlatformProvider {

    private final List<NotificationSender> notificationSenders;
    public  SendWhoopsNotificationService(List<NotificationSender> notificationSenders)
    {
  this.notificationSenders= notificationSenders;

    }
    @Override
    public void send(UUID userId, String senderName, SocialMediaPlatform sourcePlatform, String message) {
        for (NotificationSender sender:notificationSenders) {
            if(sender.platform()!=sourcePlatform)
            {
                sender.send(userId,senderName,sourcePlatform,message);
            }
        }
    }

    @Override
    public Set<SocialMediaPlatform> getConnectedPlatforms(UUID userId) {

    }
}
