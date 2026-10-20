package com.hexagonal_architecture_example.whoops.adapter.out.messaging;

import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;


@Configuration
public class RabbitMqConfiguration {
public static  final  String EXCHANGE_NAME ="whoops.exchange";
public static final String QUEUE_NAME="whoops.created.queue";
public static final String ROUTING_KEY="whoops.created";

@Bean
    public DirectExchange whoopsExchange()
{
    return  new DirectExchange(EXCHANGE_NAME);
}
@Bean
    public Queue whoopsCreatedQueue()
{
    return  new Queue(QUEUE_NAME);
}
@Bean
    public Binding whoopsCreatedBinding(Queue whoopsCreatedQueue,DirectExchange whoopsExchange)
{
return BindingBuilder
        .bind(whoopsCreatedQueue)
        .to(whoopsExchange)
        .with(ROUTING_KEY);
}
@Bean
    public MessageConverter messageConverter()
{
    return new JacksonJsonMessageConverter();
}
}
