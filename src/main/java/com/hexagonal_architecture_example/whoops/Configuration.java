package com.hexagonal_architecture_example.whoops;

import com.hexagonal_architecture_example.whoops.adapter.out.messaging.RabbitMqEventPublisher;
import com.hexagonal_architecture_example.whoops.adapter.out.persistence.JpaWhoopsRepositoryAdapter;
import com.hexagonal_architecture_example.whoops.adapter.out.persistence.SpringDataWhoopsRepository;
import com.hexagonal_architecture_example.whoops.application.port.in.CreateWhoopUseCase;
import com.hexagonal_architecture_example.whoops.application.port.out.WhoopsEventPublisher;
import com.hexagonal_architecture_example.whoops.application.port.out.WhoopsRepository;
import com.hexagonal_architecture_example.whoops.application.service.CreateWhoopsService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;


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
}