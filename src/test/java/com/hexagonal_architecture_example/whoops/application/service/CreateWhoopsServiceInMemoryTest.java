
package com.hexagonal_architecture_example.whoops.application.service;

import com.hexagonal_architecture_example.whoops.adapter.out.messaging.InMemoryWhoopsEventPublisher;
import com.hexagonal_architecture_example.whoops.adapter.out.persistence.InMemoryWhoopsRepository;
import com.hexagonal_architecture_example.whoops.application.port.out.UserRepository;
import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;
import com.hexagonal_architecture_example.whoops.domain.whoops.Whoops;
import com.hexagonal_architecture_example.whoops.domain.user.User;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreateWhoopsServiceInMemoryTest {

    @Test
    void shouldCreateWhoopsUsingInMemoryAdapters() {

        InMemoryWhoopsRepository repository = new InMemoryWhoopsRepository();
        InMemoryWhoopsEventPublisher publisher =
                mock(InMemoryWhoopsEventPublisher.class);
        UserRepository userRepository = mock(UserRepository.class);

        UUID userId = UUID.randomUUID();

        // Kullanıcının mevcut olduğunu simüle ediyoruz.
        User user = mock(User.class);
        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        CreateWhoopsService service =
                new CreateWhoopsService(repository, publisher, userRepository);

        String message = "There is a new Whoops Notification";
        String senderName = "Angela Wants Her Cat Back!";
        SocialMediaPlatform sourcePlatform = SocialMediaPlatform.X;

        Whoops result = service.create(
                userId,
                senderName,
                sourcePlatform,
                message
        );

        Optional<Whoops> savedWhoops = repository.findById(result.getId());

        assertTrue(savedWhoops.isPresent());

        Whoops storedWhoops = savedWhoops.get();

        assertEquals(result.getId(), storedWhoops.getId());
        assertEquals(result.getUserId(), storedWhoops.getUserId());
        assertEquals(result.getMessage(), storedWhoops.getMessage());
    }
}
