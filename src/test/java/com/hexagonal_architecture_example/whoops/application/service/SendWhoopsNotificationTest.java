package com.hexagonal_architecture_example.whoops.application.service;

import com.hexagonal_architecture_example.whoops.application.port.out.ConnectedPlatformProvider;
import com.hexagonal_architecture_example.whoops.application.port.out.NotificationSender;
import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import static org.mockito.Mockito.*;

public class SendWhoopsNotificationTest {
    @Test
    void shouldSendNotificationToAllPlatformsExceptSourcePlatform()
    {
        NotificationSender facebookSender = mock(NotificationSender.class);
        NotificationSender instagramSender = mock(NotificationSender.class);
        NotificationSender whatsappSender = mock(NotificationSender.class);
        when(facebookSender.platform()).thenReturn(SocialMediaPlatform.FACEBOOK);
        when(instagramSender.platform()).thenReturn(SocialMediaPlatform.INSTAGRAM);
        when(whatsappSender.platform()).thenReturn(SocialMediaPlatform.WHATSAPP);
        ConnectedPlatformProvider provider=mock(ConnectedPlatformProvider.class);

        SendWhoopsNotificationService service= new SendWhoopsNotificationService(List.
                of(
                        facebookSender,
                        instagramSender,
                        whatsappSender), provider);

        UUID userId= UUID.randomUUID();
        String message= "Ryan says Hello!";
        String senderName="Ryan Howard";
        SocialMediaPlatform sourcePlatform=SocialMediaPlatform.WHATSAPP;

        service.send(userId,senderName,sourcePlatform,message);
        verify(facebookSender,times(1)).send(userId,senderName,sourcePlatform,message);
        verify(instagramSender,times(1)).send(userId,senderName,sourcePlatform,message);
        verify(whatsappSender, never())
                .send(userId, senderName, sourcePlatform, message);
    }
}
