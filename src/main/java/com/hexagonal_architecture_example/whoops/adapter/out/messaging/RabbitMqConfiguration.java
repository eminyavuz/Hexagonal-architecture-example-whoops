package com.hexagonal_architecture_example.whoops.adapter.out.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMqConfiguration {
    public static final String EXCHANGE_NAME = "whoops.exchange";
    public static final String QUEUE_NAME = "whoops.created.queue";
    public static final String ROUTING_KEY = "whoops.created";

    public static final String DLX_EXCHANGE_NAME = "whoops.dlx";
    public static final String DLQ_QUEUE_NAME = "whoops.dlq";
    public static final String DLQ_ROUTING_KEY = "whoops.dead";


    @Bean
    public DirectExchange whoopsExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }


    @Bean
    public Queue whoopsCreatedQueue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .deadLetterExchange(DLX_EXCHANGE_NAME)
                .deadLetterRoutingKey(DLQ_ROUTING_KEY).build();
    }

    @Bean
    public Binding whoopsCreatedBinding(Queue whoopsCreatedQueue, DirectExchange whoopsExchange) {
        return BindingBuilder
                .bind(whoopsCreatedQueue)
                .to(whoopsExchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public DirectExchange whoopsDeadLetterExchange() {
        return new DirectExchange(DLX_EXCHANGE_NAME);
    }

    @Bean
    public Queue whoopsDeadLetterQueue() {
        return QueueBuilder.durable(DLQ_QUEUE_NAME).build();
    }

    @Bean
    public Binding whoopsDeadLetterBinding(Queue whoopsDeadLetterQueue,
                                           DirectExchange whoopsDeadLetterExchange) {
        return BindingBuilder.
                bind(whoopsDeadLetterQueue)
                .to(whoopsDeadLetterExchange)
                .with(DLQ_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
