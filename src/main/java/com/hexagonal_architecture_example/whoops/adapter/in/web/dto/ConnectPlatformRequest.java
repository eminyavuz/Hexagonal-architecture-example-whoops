package com.hexagonal_architecture_example.whoops.adapter.in.web.dto;

import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;
import jakarta.validation.constraints.NotNull;

public record ConnectPlatformRequest(@NotNull SocialMediaPlatform platform) {
}
