package com.hexagonal_architecture_example.whoops.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserJpaEntity {
    @Id
    @Column(name = "id",nullable = false ,updatable = false)
    private UUID id;
    @Column(name = "user_name",nullable = false,updatable = false)
    private  String userName;
    @Column(name="created_at",nullable = false,updatable = false)
    private Instant createdAt;


    protected UserJpaEntity() {
    }

    public UserJpaEntity(UUID id, String userName, Instant createdAt) {
        this.id = id;
        this.userName = userName;
        this.createdAt = createdAt;
    }

    public UUID getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
