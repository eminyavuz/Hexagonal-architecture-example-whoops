package com.hexagonal_architecture_example.whoops.application.service;

import com.hexagonal_architecture_example.whoops.application.port.in.CreateWhoopUseCase;
import com.hexagonal_architecture_example.whoops.application.port.out.UserRepository;
import com.hexagonal_architecture_example.whoops.application.port.out.WhoopsEventPublisher;
import com.hexagonal_architecture_example.whoops.application.port.out.WhoopsRepository;
import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;
import com.hexagonal_architecture_example.whoops.domain.whoops.Whoops;
import com.hexagonal_architecture_example.whoops.domain.whoops.WhoopsCreatedEvent;

import java.util.UUID;

public class CreateWhoopsService implements CreateWhoopUseCase {
    private final WhoopsRepository whoopsRepository;
    private final WhoopsEventPublisher eventPublisher;
    private final UserRepository userRepository;
    public CreateWhoopsService(
            WhoopsRepository whoopsRepository,
            WhoopsEventPublisher eventPublisher,
            UserRepository userRepository
    )
    {
        this.whoopsRepository=whoopsRepository;
        this.eventPublisher=eventPublisher;
        this.userRepository=userRepository;
    }

    @Override
    public Whoops create(UUID userId, String senderName, SocialMediaPlatform sourcePlatform, String message) {
        if(userRepository.findById(userId).isPresent()){
        Whoops whoops=Whoops.create(userId,senderName,sourcePlatform,message);
        whoopsRepository.save(whoops);
       WhoopsCreatedEvent event= new WhoopsCreatedEvent(whoops.getId(),whoops.getUserId(),whoops.getSenderName(),whoops.getSourcePlatform(),whoops.getMessage(),whoops.getCreatedAt());
        eventPublisher.publish(event);
        return  whoops;
        }
        else
            throw  new IllegalArgumentException("User does not exists");
    }
}
