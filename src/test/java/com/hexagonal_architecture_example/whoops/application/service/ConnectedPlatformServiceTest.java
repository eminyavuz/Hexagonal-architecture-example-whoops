package com.hexagonal_architecture_example.whoops.application.service;

import com.hexagonal_architecture_example.whoops.application.port.out.ConnectedPlatformRepository;
import com.hexagonal_architecture_example.whoops.application.port.out.UserRepository;
import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;
import com.hexagonal_architecture_example.whoops.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConnectedPlatformServiceTest {

    private UserRepository userRepository;
    private ConnectedPlatformRepository platformRepository;
    private ConnectedPlatformService service;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        platformRepository = mock(ConnectedPlatformRepository.class);

        service = new ConnectedPlatformService(
                userRepository,
                platformRepository
        );
    }

    @Test
    void shouldConnectPlatform() {
        UUID userId = UUID.randomUUID();
        SocialMediaPlatform platform = SocialMediaPlatform.INSTAGRAM;

        User user = User.create("Dwight");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(platformRepository.exists(userId, platform))
                .thenReturn(false);

        assertDoesNotThrow(() -> service.connect(userId, platform));

        verify(platformRepository).save(userId, platform);
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        UUID userId = UUID.randomUUID();
        SocialMediaPlatform platform = SocialMediaPlatform.INSTAGRAM;

        when(userRepository.findById(userId))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.connect(userId, platform)
        );

        verifyNoInteractions(platformRepository);
    }

    @Test
    void shouldThrowWhenPlatformAlreadyConnected() {
        UUID userId = UUID.randomUUID();
        SocialMediaPlatform platform = SocialMediaPlatform.INSTAGRAM;

        User user = User.create("Dwight");

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        when(platformRepository.exists(userId, platform))
                .thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.connect(userId, platform)
        );

        verify(platformRepository, never()).save(any(), any());
    }
}