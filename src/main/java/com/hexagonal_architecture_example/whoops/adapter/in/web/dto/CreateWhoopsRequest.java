package com.hexagonal_architecture_example.whoops.adapter.in.web.dto;


import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;

import java.util.UUID;

public record CreateWhoopsRequest(UUID userId, String senderName, SocialMediaPlatform sourcePlatform, String message) {


}
