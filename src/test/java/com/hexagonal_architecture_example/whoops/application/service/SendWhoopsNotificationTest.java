package com.hexagonal_architecture_example.whoops.application.service;

import com.hexagonal_architecture_example.whoops.application.port.out.ConnectedPlatformProvider;
import com.hexagonal_architecture_example.whoops.application.port.out.NotificationSender;
import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.*;

class SendWhoopsNotificationServiceTest {

    private ConnectedPlatformProvider connectedPlatformProvider;
    private NotificationSender instagramSender;
    private NotificationSender whatsappSender;
    private NotificationSender facebookSender;
    private NotificationSender snapchatSender;

    private SendWhoopsNotificationService service;

    @BeforeEach
    void setUp() {
        connectedPlatformProvider = mock(ConnectedPlatformProvider.class);

        instagramSender = createSender(SocialMediaPlatform.INSTAGRAM);
        whatsappSender = createSender(SocialMediaPlatform.WHATSAPP);
        facebookSender = createSender(SocialMediaPlatform.FACEBOOK);
        snapchatSender = createSender(SocialMediaPlatform.SNAPCHAT);

        service = new SendWhoopsNotificationService(
                List.of(instagramSender, whatsappSender, facebookSender, snapchatSender),
                connectedPlatformProvider
        );
    }

    private NotificationSender createSender(SocialMediaPlatform platform) {
        NotificationSender sender = mock(NotificationSender.class);
        when(sender.platform()).thenReturn(platform);
        return sender;
    }

    @Test
    void shouldSendNotificationsOnlyToConnectedPlatforms() {
        UUID userId = UUID.randomUUID();

        when(connectedPlatformProvider.getConnectedPlatforms(userId))
                .thenReturn(Set.of(
                        SocialMediaPlatform.INSTAGRAM,
                        SocialMediaPlatform.WHATSAPP
                ));

        service.send(
                userId,
                "Jim Halpert",
                SocialMediaPlatform.FACEBOOK,
                "Hey, Dwight!"
        );

        verify(instagramSender).send(
                userId, "Jim Halpert", SocialMediaPlatform.FACEBOOK, "Hey, Dwight!"
        );

        verify(whatsappSender).send(
                userId, "Jim Halpert", SocialMediaPlatform.FACEBOOK, "Hey, Dwight!"
        );

        verify(facebookSender, never()).send(any(), any(), any(), any());
        verify(snapchatSender, never()).send(any(), any(), any(), any());    }

    @Test
    void shouldNotSendNotificationToSourcePlatform() {
        UUID userId = UUID.randomUUID();

        when(connectedPlatformProvider.getConnectedPlatforms(userId))
                .thenReturn(Set.of(
                        SocialMediaPlatform.FACEBOOK,
                        SocialMediaPlatform.INSTAGRAM
                ));

        service.send(
                userId,
                "Jim Halpert",
                SocialMediaPlatform.FACEBOOK,
                "Hey, Dwight!"
        );

        verify(instagramSender).send(
                userId, "Jim Halpert", SocialMediaPlatform.FACEBOOK, "Hey, Dwight!"
        );

        verify(facebookSender, never()).send(any(), any(), any(), any());    }

    @Test
    void shouldNotSendNotificationsWhenNoPlatformsAreConnected() {
        UUID userId = UUID.randomUUID();

        when(connectedPlatformProvider.getConnectedPlatforms(userId))
                .thenReturn(Set.of());

        service.send(
                userId,
                "Jim Halpert",
                SocialMediaPlatform.FACEBOOK,
                "Hey, Dwight!"
        );

        verify(instagramSender, never()).send(any(), any(), any(), any());
        verify(whatsappSender, never()).send(any(), any(), any(), any());
        verify(facebookSender, never()).send(any(), any(), any(), any());
        verify(snapchatSender, never()).send(any(), any(), any(), any());
    }

    @Test
    void shouldSendNotificationsToAllConnectedPlatformsExceptSourcePlatform() {
        UUID userId = UUID.randomUUID();

        when(connectedPlatformProvider.getConnectedPlatforms(userId))
                .thenReturn(Set.of(
                        SocialMediaPlatform.INSTAGRAM,
                        SocialMediaPlatform.WHATSAPP,
                        SocialMediaPlatform.FACEBOOK,
                        SocialMediaPlatform.SNAPCHAT
                ));

        service.send(
                userId,
                "Jim Halpert",
                SocialMediaPlatform.FACEBOOK,
                "Hey, Dwight!"
        );

        verify(instagramSender).send(
                userId, "Jim Halpert", SocialMediaPlatform.FACEBOOK, "Hey, Dwight!"
        );

        verify(whatsappSender).send(
                userId, "Jim Halpert", SocialMediaPlatform.FACEBOOK, "Hey, Dwight!"
        );

        verify(snapchatSender).send(
                userId, "Jim Halpert", SocialMediaPlatform.FACEBOOK, "Hey, Dwight!"
        );

        verify(facebookSender, never())
                .send(any(), any(), any(), any());
    }

    @Test
    void shouldContinueSendingNotificationsWhenOnePlatformFails() {
        UUID userId = UUID.randomUUID();

        when(connectedPlatformProvider.getConnectedPlatforms(userId))
                .thenReturn(Set.of(
                        SocialMediaPlatform.INSTAGRAM,
                        SocialMediaPlatform.WHATSAPP,
                        SocialMediaPlatform.FACEBOOK,
                        SocialMediaPlatform.SNAPCHAT
                ));

        // Instagram gönderimi hata verecek.
        doThrow(new RuntimeException("Instagram notification failed"))
                .when(instagramSender)
                .send(
                        any(UUID.class),
                        anyString(),
                        eq(SocialMediaPlatform.FACEBOOK),
                        anyString()
                );

        service.send(
                userId,
                "Jim Halpert",
                SocialMediaPlatform.FACEBOOK,
                "Hey, Dwight!"
        );

        // Instagram gönderiminin denendiğini doğrula.
        verify(instagramSender).send(
                userId,
                "Jim Halpert",
                SocialMediaPlatform.FACEBOOK,
                "Hey, Dwight!"
        );

        // Instagram hatasına rağmen diğer gönderimler gerçekleşmeli.
        verify(whatsappSender).send(
                userId,
                "Jim Halpert",
                SocialMediaPlatform.FACEBOOK,
                "Hey, Dwight!"
        );

        verify(snapchatSender).send(
                userId,
                "Jim Halpert",
                SocialMediaPlatform.FACEBOOK,
                "Hey, Dwight!"
        );

        // Kaynak platforma gönderim yapılmamalı.
        verify(facebookSender, never())
                .send(any(), any(), any(), any());
    }
}