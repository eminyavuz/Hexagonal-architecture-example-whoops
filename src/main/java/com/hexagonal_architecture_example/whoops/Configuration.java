package com.hexagonal_architecture_example.whoops;

import com.hexagonal_architecture_example.whoops.adapter.out.messaging.InMemoryWhoopsEventPublisher;
import com.hexagonal_architecture_example.whoops.adapter.out.persistence.InMemoryWhoopsRepository;
import com.hexagonal_architecture_example.whoops.application.port.in.CreateWhoopUseCase;
import com.hexagonal_architecture_example.whoops.application.port.out.WhoopsEventPublisher;
import com.hexagonal_architecture_example.whoops.application.port.out.WhoopsRepository;
import com.hexagonal_architecture_example.whoops.application.service.CreateWhoopsService;
import org.springframework.context.annotation.Bean;


@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public InMemoryWhoopsEventPublisher whoopsEventPublisher() {
        return new InMemoryWhoopsEventPublisher();
    }

    @Bean
    public InMemoryWhoopsRepository whoopsRepository() {
        return new InMemoryWhoopsRepository();
    }

    @Bean
    public CreateWhoopUseCase createWhoopUseCase(
            WhoopsRepository repository,
            WhoopsEventPublisher publisher
    ) {
        return new CreateWhoopsService(repository, publisher);
    }
}