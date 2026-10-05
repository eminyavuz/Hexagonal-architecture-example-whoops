package com.hexagonal_architecture_example.whoops.application.port.in;

import com.hexagonal_architecture_example.whoops.domain.Whoops;

import java.util.UUID;

public interface CreateWhoopUseCase {
    Whoops create(UUID userId, String message);
}
