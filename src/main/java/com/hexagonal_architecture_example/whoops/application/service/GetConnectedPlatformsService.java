package com.hexagonal_architecture_example.whoops.application.service;

import com.hexagonal_architecture_example.whoops.application.port.in.GetConnectedPlatformsUseCase;
import com.hexagonal_architecture_example.whoops.application.port.out.ConnectedPlatformProvider;
import com.hexagonal_architecture_example.whoops.application.port.out.UserRepository;
import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;

import java.util.Set;
import java.util.UUID;

public class GetConnectedPlatformsService implements GetConnectedPlatformsUseCase {
    private final UserRepository userRepository;
    private  final ConnectedPlatformProvider platformProvider;

    public  GetConnectedPlatformsService(UserRepository userRepository,ConnectedPlatformProvider platformProvider)
    {
        this.userRepository=userRepository;
        this.platformProvider=platformProvider;
    }

    @Override
    public Set<SocialMediaPlatform> getConnectedPlatforms(UUID userId) {
     if(userRepository.findById(userId).isEmpty())
         throw  new IllegalArgumentException("User not found");

     return platformProvider.getConnectedPlatforms(userId);
    }
}
