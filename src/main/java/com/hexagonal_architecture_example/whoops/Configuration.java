package com.hexagonal_architecture_example.whoops;

import com.hexagonal_architecture_example.whoops.adapter.in.messaging.RabbitMqWhoopsCreatedListener;
import com.hexagonal_architecture_example.whoops.adapter.out.messaging.RabbitMqEventPublisher;
import com.hexagonal_architecture_example.whoops.adapter.out.notification.*;
import com.hexagonal_architecture_example.whoops.adapter.out.persistence.JpaConnectedPlatformProvider;
import com.hexagonal_architecture_example.whoops.adapter.out.persistence.JpaWhoopsRepositoryAdapter;
import com.hexagonal_architecture_example.whoops.adapter.out.persistence.SpringDataConnectedPlatformRepository;
import com.hexagonal_architecture_example.whoops.adapter.out.persistence.SpringDataWhoopsRepository;
import com.hexagonal_architecture_example.whoops.application.port.in.CreateWhoopUseCase;
import com.hexagonal_architecture_example.whoops.application.port.in.SendWhoopNotificationUseCase;
import com.hexagonal_architecture_example.whoops.application.port.out.ConnectedPlatformProvider;
import com.hexagonal_architecture_example.whoops.application.port.out.NotificationSender;
import com.hexagonal_architecture_example.whoops.application.port.out.WhoopsEventPublisher;
import com.hexagonal_architecture_example.whoops.application.port.out.WhoopsRepository;
import com.hexagonal_architecture_example.whoops.application.service.CreateWhoopsService;
import com.hexagonal_architecture_example.whoops.application.service.SendWhoopsNotificationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;

import java.util.List;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public WhoopsEventPublisher whoopsEventPublisher(RabbitTemplate rabbitTemplate) {
        return new RabbitMqEventPublisher(rabbitTemplate);
    }

    @Bean
    public WhoopsRepository whoopsRepository(SpringDataWhoopsRepository repository) {
        return new JpaWhoopsRepositoryAdapter(repository);
    }

    @Bean
    public CreateWhoopUseCase createWhoopUseCase(
            WhoopsRepository repository,
            WhoopsEventPublisher publisher
    ) {
        return new CreateWhoopsService(repository, publisher);
    }
    @Bean
    public SendWhoopNotificationUseCase sendWhoopNotificationUseCase (List<NotificationSender> senders,ConnectedPlatformProvider connectedPlatformProvider)
    {
        return new SendWhoopsNotificationService(senders,connectedPlatformProvider);
    }
    @Bean
    public RabbitMqWhoopsCreatedListener rabbitMqWhoopsCreatedListener(SendWhoopNotificationUseCase useCase)
    {
        return new RabbitMqWhoopsCreatedListener(useCase);
    }
    @Bean
    public NotificationSender instagramNotificationSender()
    {
        return new InstagramNotificationAdapter();
    }
    @Bean
    public NotificationSender whatsappNotificationSender()
    {
        return new WhatsappNotificationAdapter();
    }
    @Bean
    public NotificationSender snapchatNotificationSender()
    {
        return new SnapchatNotificationAdapter();
    }
    @Bean
    public NotificationSender xNotificationSender()
    {
        return  new XNotificationAdapter();
    }
    @Bean
    public NotificationSender facebookNotificationSender()
    {
        return new FacebookNotificationAdapter();
    }
    @Bean
    public ConnectedPlatformProvider connectedPlatformProvider(SpringDataConnectedPlatformRepository repository)
    {
        return new JpaConnectedPlatformProvider(repository);
    }



}