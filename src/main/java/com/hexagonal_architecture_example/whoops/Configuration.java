package com.hexagonal_architecture_example.whoops;

import com.hexagonal_architecture_example.whoops.adapter.in.messaging.RabbitMqWhoopsCreatedListener;
import com.hexagonal_architecture_example.whoops.adapter.out.messaging.RabbitMqEventPublisher;
import com.hexagonal_architecture_example.whoops.adapter.out.notification.*;
import com.hexagonal_architecture_example.whoops.adapter.out.persistence.*;
import com.hexagonal_architecture_example.whoops.application.port.in.*;
import com.hexagonal_architecture_example.whoops.application.port.out.*;
import com.hexagonal_architecture_example.whoops.application.service.*;
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
    public ConnectedPlatformUseCase connectedPlatformUseCase(UserRepository userRepository,ConnectedPlatformRepository platformRepository)
    {
        return new ConnectedPlatformService(userRepository,platformRepository);
    }

    @Bean
    public WhoopsRepository whoopsRepository(SpringDataWhoopsRepository repository) {
        return new JpaWhoopsRepositoryAdapter(repository);
    }
    @Bean
    public UserRepository  userRepository(SpringDataUserRepository repository)
    {
        return new JpaUserRepositoryAdapter(repository);
    }
    @Bean
    public GetConnectedPlatformsUseCase getConnectedPlatformsUseCase(UserRepository userRepository,ConnectedPlatformProvider platformProvider)
    {
        return new GetConnectedPlatformsService(userRepository,platformProvider);
    }
@Bean
public ConnectedPlatformRepository connectedPlatformRepository(SpringDataConnectedPlatformRepository repository)
{
    return new JpaConnectedPlatformRepositoryAdapter(repository);
}
    @Bean
    public CreateWhoopUseCase createWhoopUseCase(
            WhoopsRepository repository,
            WhoopsEventPublisher publisher,
            UserRepository userRepository
    ) {
        return new CreateWhoopsService(repository, publisher, userRepository);
    }
    @Bean
    public CreateUserUseCase createUserUseCase(UserRepository repository)
    {
        return new CreateUserService(repository);
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