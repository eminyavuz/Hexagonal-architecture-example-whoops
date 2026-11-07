package com.hexagonal_architecture_example.whoops.application.service;

import com.hexagonal_architecture_example.whoops.application.port.in.ConnectedPlatformUseCase;
import com.hexagonal_architecture_example.whoops.application.port.out.ConnectedPlatformRepository;
import com.hexagonal_architecture_example.whoops.application.port.out.UserRepository;
import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;

import java.util.UUID;

public class ConnectedPlatformService implements ConnectedPlatformUseCase {
    private final UserRepository userRepository;
    private final ConnectedPlatformRepository platformRepository;

    public ConnectedPlatformService(UserRepository userRepository, ConnectedPlatformRepository connectedPlatformRepository)
    {
        this.platformRepository=connectedPlatformRepository;
        this.userRepository=userRepository;
    }
@Override
    public void connect(UUID userId, SocialMediaPlatform platform)
    {
        if(userRepository.findById(userId).isEmpty())
        {
            throw  new IllegalArgumentException("User not found");
        }
        if(platformRepository.exists(userId,platform))
            throw new IllegalArgumentException("Connection already exists");
        platformRepository.save(userId,platform);
    }
}
