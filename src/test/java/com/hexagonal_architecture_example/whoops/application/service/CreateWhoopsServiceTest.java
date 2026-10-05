package com.hexagonal_architecture_example.whoops.application.service;

import com.hexagonal_architecture_example.whoops.application.port.out.WhoopsEventPublisher;
import com.hexagonal_architecture_example.whoops.application.port.out.WhoopsRepository;
import com.hexagonal_architecture_example.whoops.domain.Whoops;
import com.hexagonal_architecture_example.whoops.domain.WhoopsCreatedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class CreateWhoopsServiceTest {
    private WhoopsRepository whoopsRepository;
    private WhoopsEventPublisher publisher;
    private  CreateWhoopsService service;
    @BeforeEach
    void setup()
    {
        whoopsRepository=mock(WhoopsRepository.class);
        publisher=mock(WhoopsEventPublisher.class);

        service= new CreateWhoopsService(whoopsRepository, publisher);
    }

    @Test
    void ShouldCreateSaveAndPublishWhoops()
    {
        UUID userid= UUID.randomUUID();
        String message= "You have new  Whoops!";

        Whoops result= service.create(userid,message);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getCreatedAt());

        assertEquals(userid,result.getUserId());
        assertEquals(message,result.getMessage());

        verify(whoopsRepository).save(result);


        ArgumentCaptor<WhoopsCreatedEvent> eventCaptor=
                ArgumentCaptor.forClass(WhoopsCreatedEvent.class);
        verify(publisher).publish(eventCaptor.capture());

        WhoopsCreatedEvent event= eventCaptor.getValue();

        assertEquals(result.getId(),event.whoopsId());
        assertEquals(result.getUserId(),event.userId());
        assertEquals(result.getMessage(),event.message());
        assertEquals(result.getCreatedAt(),event.occurredAt());


    }
}
