package com.hexagonal_architecture_example.whoops.adapter.out.persistence;

import com.hexagonal_architecture_example.whoops.application.port.out.ConnectedPlatformRepository;
import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;

import java.util.UUID;

public class JpaConnectedPlatformRepositoryAdapter implements ConnectedPlatformRepository {
    private final SpringDataConnectedPlatformRepository repository;

    public JpaConnectedPlatformRepositoryAdapter(
            SpringDataConnectedPlatformRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(UUID userId, SocialMediaPlatform platform) {
        UserConnectedPlatformEntity entity= new UserConnectedPlatformEntity(UUID.randomUUID(),userId,platform);
     repository.save(entity);
    }

    @Override
    public boolean exists(UUID userId, SocialMediaPlatform platform) {
      return repository.existsByUserIdAndPlatform(userId,platform);
    }
}
