package com.hexagonal_architecture_example.whoops.adapter.out.persistence;

import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(
        name = "user_connected_platforms",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"user_id", "platform"}
                )
        }
)
public class UserConnectedPlatformEntity {
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "platform", nullable = false)
    private SocialMediaPlatform platform;

protected UserConnectedPlatformEntity(){}
    public UserConnectedPlatformEntity(UUID id, UUID userId,SocialMediaPlatform platform)
    {
        this.id=id;
        this.userId=userId;
        this.platform=platform;
    }

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public SocialMediaPlatform getPlatform() {
        return platform;
    }
}
