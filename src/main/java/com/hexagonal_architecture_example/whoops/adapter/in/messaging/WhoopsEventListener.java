package com.hexagonal_architecture_example.whoops.adapter.in.messaging;

import com.hexagonal_architecture_example.whoops.application.port.in.SendWhoopNotificationUseCase;
import com.hexagonal_architecture_example.whoops.domain.whoops.WhoopsCreatedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class WhoopsEventListener {

    private  final SendWhoopNotificationUseCase useCase;
    public WhoopsEventListener(SendWhoopNotificationUseCase useCase)
    {
        this.useCase=useCase;
    }
@RabbitListener(queues = "whoops.created.queue")
    public void handle(WhoopsCreatedEvent event)
{
    useCase.send(
            event.userId(),
            event.senderName(),
            event.sourcePlatform()
            ,event.message()
    );
}

}
