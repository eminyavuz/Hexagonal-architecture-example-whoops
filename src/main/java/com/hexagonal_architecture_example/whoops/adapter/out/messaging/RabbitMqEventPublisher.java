package com.hexagonal_architecture_example.whoops.adapter.out.messaging;

import com.hexagonal_architecture_example.whoops.application.port.out.WhoopsEventPublisher;
import com.hexagonal_architecture_example.whoops.domain.WhoopsCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class RabbitMqEventPublisher implements WhoopsEventPublisher {
    private final RabbitTemplate rabbitTemplate;
     public RabbitMqEventPublisher(RabbitTemplate rabbitTemplate)
     {
         this.rabbitTemplate=  rabbitTemplate;
     }
    @Override
    public void publish(WhoopsCreatedEvent event) {
  rabbitTemplate.convertAndSend(RabbitMqConfiguration.EXCHANGE_NAME,RabbitMqConfiguration.ROUTING_KEY,event);


    }
}
