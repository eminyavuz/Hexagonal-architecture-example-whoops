package com.hexagonal_architecture_example.whoops.adapter.in.web.dto;

import java.time.Instant;
import java.util.UUID;

public record UserResponse (UUID id, String userName, Instant createdDate){
}
