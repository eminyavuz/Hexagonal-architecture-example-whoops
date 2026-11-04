package com.hexagonal_architecture_example.whoops.adapter.out.persistence;

import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "whoops")
public class WhoopsJpaEntity {

    @Id
    @Column(name = "id",nullable = false)
    UUID id;
    @Column(name = "user_id",nullable = false)
    UUID userId;
    @Column(name = "sender_name",nullable = false)
    String senderName;
    @Column(name = "source_platform",nullable = false)
    @Enumerated(EnumType.STRING)
    SocialMediaPlatform sourcePlatform;
    @Column(name = "message",nullable = false)
    String message;
    @Column(name="created_at",nullable = false)
    Instant createdAt;
protected WhoopsJpaEntity()
{

}
public WhoopsJpaEntity(UUID id, UUID userId,String senderName,SocialMediaPlatform sourcePlatform,String message,Instant createdAt)
{
    this.id=id;
    this.userId= userId;
    this.message=message;
    this.createdAt=createdAt;
    this.senderName=senderName;
    this.sourcePlatform=sourcePlatform;
}

    public UUID getId() {
        return id;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getMessage() {
        return message;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getSenderName() {
        return senderName;
    }

    public SocialMediaPlatform getSourcePlatform() {
        return sourcePlatform;
    }
}

