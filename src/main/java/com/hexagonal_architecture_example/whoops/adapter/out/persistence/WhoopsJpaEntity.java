package com.hexagonal_architecture_example.whoops.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
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
    @Column(name = "message",nullable = false)
    String message;
    @Column(name="created_at",nullable = false)
    Instant createdAt;
protected WhoopsJpaEntity()
{

}
public WhoopsJpaEntity(UUID id, UUID userId,String message,Instant createdAt)
{
    this.id=id;
    this.userId= userId;
    this.message=message;
    this.createdAt=createdAt;
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
}
