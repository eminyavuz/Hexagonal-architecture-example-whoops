package com.hexagonal_architecture_example.whoops.adapter.in.messaging;

import com.hexagonal_architecture_example.whoops.adapter.in.messaging.RabbitMqWhoopsCreatedListener;
import com.hexagonal_architecture_example.whoops.application.port.in.SendWhoopNotificationUseCase;
import com.hexagonal_architecture_example.whoops.domain.SocialMediaPlatform;
import com.hexagonal_architecture_example.whoops.domain.WhoopsCreatedEvent;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class RabbitMqWhoopsCreatedEventListenerTest {
@Test
    void shouldHandleWhoopsCreatedEvent()
{
    SendWhoopNotificationUseCase useCase= mock(SendWhoopNotificationUseCase.class);
    RabbitMqWhoopsCreatedListener listener= new RabbitMqWhoopsCreatedListener(useCase);

    UUID userId= UUID.randomUUID();
    String message= "Kelly wants to talk about that film earlier";
    String senderName="Kelly";
    SocialMediaPlatform source= SocialMediaPlatform.INSTAGRAM;
    WhoopsCreatedEvent event=
            new WhoopsCreatedEvent(UUID.randomUUID(),userId,senderName,source,message, Instant.now());
    listener.handle(event);


    verify(useCase,times(1)).send(userId,senderName,source,message);
}
}
