package com.hexagonal_architecture_example.whoops.adapter.out.persistence;

import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataConnectedPlatformRepository
        extends JpaRepository<UserConnectedPlatformEntity, UUID> {

    List<UserConnectedPlatformEntity> findAllByUserId(UUID userId);

    boolean existsByUserIdAndPlatform(
            UUID userId,
            SocialMediaPlatform platform
    );
}