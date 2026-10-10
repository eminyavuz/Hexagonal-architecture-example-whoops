package com.hexagonal_architecture_example.whoops.application.service;

import com.hexagonal_architecture_example.whoops.adapter.out.messaging.InMemoryWhoopsEventPublisher;
import com.hexagonal_architecture_example.whoops.adapter.out.persistence.InMemoryWhoopsRepository;
import com.hexagonal_architecture_example.whoops.domain.Whoops;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CreateWhoopsServiceInMemoryTest {
    @Test
    void shouldCreateWhoopsUsingInMemoryAdapters()
    {
        InMemoryWhoopsRepository repository =  new InMemoryWhoopsRepository();
        InMemoryWhoopsEventPublisher publisher= new InMemoryWhoopsEventPublisher();

       CreateWhoopsService service= new CreateWhoopsService(repository,publisher);
    UUID userId= UUID.randomUUID();
    String message="There is a new Whoops Notification";
    Whoops result=service.create(userId,message);

        Optional<Whoops> savedWhoops=repository.findById(result.getId());

        assertTrue(savedWhoops.isPresent());
        Whoops storedWhoops=savedWhoops.get();

        assertEquals(result.getId(),storedWhoops.getId());
        assertEquals(result.getUserId(),storedWhoops.getUserId());
        assertEquals(result.getMessage(),storedWhoops.getMessage());
        
    }
}
