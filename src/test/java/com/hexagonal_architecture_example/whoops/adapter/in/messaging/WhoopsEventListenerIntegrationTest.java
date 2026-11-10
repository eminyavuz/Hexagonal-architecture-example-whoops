
package com.hexagonal_architecture_example.whoops.adapter.in.messaging;

import com.hexagonal_architecture_example.whoops.application.port.in.SendWhoopNotificationUseCase;
import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;
import com.hexagonal_architecture_example.whoops.domain.whoops.WhoopsCreatedEvent;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;

@SpringBootTest
@Testcontainers
class WhoopsEventListenerIntegrationTest {

    private static final String EXCHANGE = "whoops.exchange";
    private static final String ROUTING_KEY = "whoops.created";

    @Container
    static final RabbitMQContainer rabbitMQ =
            new RabbitMQContainer("rabbitmq:4-management");

    @DynamicPropertySource
    static void configureRabbitMQ(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", rabbitMQ::getHost);
        registry.add("spring.rabbitmq.port", rabbitMQ::getAmqpPort);
        registry.add("spring.rabbitmq.username", rabbitMQ::getAdminUsername);
        registry.add("spring.rabbitmq.password", rabbitMQ::getAdminPassword);
    }

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @MockitoSpyBean
    private SendWhoopNotificationUseCase notificationUseCase;

    @Test
    void shouldConsumeWhoopsCreatedEventAndSendNotification() {
        UUID userId = UUID.randomUUID();
        UUID whoopsId = UUID.randomUUID();

        WhoopsCreatedEvent event = new WhoopsCreatedEvent(
                whoopsId,
                userId,
                "Jim Halpert",
                SocialMediaPlatform.FACEBOOK,
                "Hey, Dwight!",
                Instant.now()
        );

        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, event);

        verify(notificationUseCase, timeout(5000)).send(
                userId,
                "Jim Halpert",
                SocialMediaPlatform.FACEBOOK,
                "Hey, Dwight!"
        );
    }
}
