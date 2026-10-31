package com.hexagonal_architecture_example.whoops.adapter.out.persistence;

import com.hexagonal_architecture_example.whoops.application.port.out.ConnectedPlatformProvider;
import com.hexagonal_architecture_example.whoops.domain.SocialMediaPlatform;

import java.util.Set;
import java.util.UUID;

public class JpaConnectedPlatformProvider implements ConnectedPlatformProvider {

    private final SpringDataConnectedPlatformRepository repository;
    public JpaConnectedPlatformProvider(SpringDataConnectedPlatformRepository repository)
    {
        this.repository= repository;
    }
    @Override
    public Set<SocialMediaPlatform> getConnectedPlatforms(UUID userId)
    {
        return repository.findAllByUserId(userId)
                .stream()
                .map(UserConnectedPlatformEntity::getPlatform)
                .collect(java.util.stream.Collectors.toSet());
    }
}
