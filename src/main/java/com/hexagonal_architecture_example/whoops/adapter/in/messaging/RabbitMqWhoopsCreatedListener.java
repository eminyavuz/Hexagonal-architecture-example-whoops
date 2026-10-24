package com.hexagonal_architecture_example.whoops.adapter.in.messaging;

import com.hexagonal_architecture_example.whoops.adapter.out.messaging.RabbitMqConfiguration;
import com.hexagonal_architecture_example.whoops.application.port.in.SendWhoopNotificationUseCase;
import com.hexagonal_architecture_example.whoops.domain.WhoopsCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class RabbitMqWhoopsCreatedListener {
    private final SendWhoopNotificationUseCase useCase;
    public RabbitMqWhoopsCreatedListener(SendWhoopNotificationUseCase useCase)
    {
        this.useCase=useCase;
    }

    @RabbitListener(queues = RabbitMqConfiguration.QUEUE_NAME)
    public  void handle(WhoopsCreatedEvent event)
    {
        useCase.send(event.userId(),event.senderName(),event.sourcePlatform(),event.message());
    }
}
