package com.hexagonal_architecture_example.whoops.application.service;

import com.hexagonal_architecture_example.whoops.application.port.in.CreateWhoopUseCase;
import com.hexagonal_architecture_example.whoops.application.port.out.WhoopsEventPublisher;
import com.hexagonal_architecture_example.whoops.application.port.out.WhoopsRepository;
import com.hexagonal_architecture_example.whoops.domain.Whoops;
import com.hexagonal_architecture_example.whoops.domain.WhoopsCreatedEvent;

import java.util.UUID;

public class CreateWhoopsService implements CreateWhoopUseCase {
    private final WhoopsRepository whoopsRepository;
    private final WhoopsEventPublisher eventPublisher;
    public CreateWhoopsService(
            WhoopsRepository whoopsRepository,
            WhoopsEventPublisher eventPublisher
    )
    {
        this.whoopsRepository=whoopsRepository;
        this.eventPublisher=eventPublisher;
    }

    @Override
    public Whoops create(UUID userId, String message) {
        Whoops whoops=Whoops.create(userId,message);
        whoopsRepository.save(whoops);
       WhoopsCreatedEvent event= new WhoopsCreatedEvent(whoops.getId(),whoops.getUserId(),whoops.getMessage(),whoops.getCreatedAt());
        eventPublisher.publish(event);

        return whoops;
    }
}
