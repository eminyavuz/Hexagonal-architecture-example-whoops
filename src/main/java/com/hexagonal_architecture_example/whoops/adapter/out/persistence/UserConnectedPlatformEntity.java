package com.hexagonal_architecture_example.whoops.adapter.out.persistence;

import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
 @Table(name = "user_connected_platforms")
public class UserConnectedPlatformEntity {
   @Id
    @Column(name = "id")
    UUID id;
   @Column(name="user_id",nullable = false)
    UUID userId;
   @Column(name="platform")
     @Enumerated(EnumType.STRING)
    SocialMediaPlatform platform;

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
