package com.hexagonal_architecture_example.whoops.application.service;

import com.hexagonal_architecture_example.whoops.application.port.in.SendWhoopNotificationUseCase;
import com.hexagonal_architecture_example.whoops.application.port.out.ConnectedPlatformProvider;
import com.hexagonal_architecture_example.whoops.application.port.out.NotificationSender;
import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class SendWhoopsNotificationService implements SendWhoopNotificationUseCase  {

    private final List<NotificationSender> notificationSenders;
    private final ConnectedPlatformProvider connectedPlatformProvider;
    public  SendWhoopsNotificationService(List<NotificationSender> notificationSenders,ConnectedPlatformProvider connectedPlatformProvider)
    {
  this.notificationSenders= notificationSenders;
  this.connectedPlatformProvider= connectedPlatformProvider;


    }
    @Override
    public void send(UUID userId, String senderName, SocialMediaPlatform sourcePlatform, String message) {
        Set<SocialMediaPlatform> connectedPlatforms=connectedPlatformProvider.getConnectedPlatforms(userId);

        for (NotificationSender sender:notificationSenders) {
            if(sender.platform()!=sourcePlatform && connectedPlatforms.contains(sender.platform()))
            {
                sender.send(userId,senderName,sourcePlatform,message);
            }
        }
    }

}
