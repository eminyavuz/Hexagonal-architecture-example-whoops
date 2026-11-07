package com.hexagonal_architecture_example.whoops.application.service;

import com.hexagonal_architecture_example.whoops.application.port.out.ConnectedPlatformProvider;
import com.hexagonal_architecture_example.whoops.application.port.out.UserRepository;
import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;
import com.hexagonal_architecture_example.whoops.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetConnectedPlatformsServiceTest {

    private UserRepository userRepository;
    private ConnectedPlatformProvider platformProvider;
    private GetConnectedPlatformsService service;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        platformProvider = mock(ConnectedPlatformProvider.class);

        service = new GetConnectedPlatformsService(
                userRepository,
                platformProvider
        );
    }

    @Test
    void shouldReturnConnectedPlatforms() {
        UUID userId = UUID.randomUUID();

        User user = User.create("Dwight");

        Set<SocialMediaPlatform> platforms = Set.of(
                SocialMediaPlatform.INSTAGRAM,
                SocialMediaPlatform.WHATSAPP
        );

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(platformProvider.getConnectedPlatforms(userId))
                .thenReturn(platforms);

        Set<SocialMediaPlatform> result =
                service.getConnectedPlatforms(userId);

        assertEquals(platforms, result);
        verify(platformProvider).getConnectedPlatforms(userId);
    }

    @Test
    void shouldReturnEmptySetWhenUserHasNoConnectedPlatforms() {
        UUID userId = UUID.randomUUID();

        User user = User.create("Dwight");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(platformProvider.getConnectedPlatforms(userId))
                .thenReturn(Collections.emptySet());

        Set<SocialMediaPlatform> result =
                service.getConnectedPlatforms(userId);

        assertTrue(result.isEmpty());
        verify(platformProvider).getConnectedPlatforms(userId);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        UUID userId = UUID.randomUUID();

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.getConnectedPlatforms(userId)
        );

        verifyNoInteractions(platformProvider);
    }
}