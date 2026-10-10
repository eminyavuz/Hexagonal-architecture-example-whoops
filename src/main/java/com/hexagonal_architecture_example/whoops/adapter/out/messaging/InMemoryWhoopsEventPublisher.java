package com.hexagonal_architecture_example.whoops.adapter.out.messaging;

import com.hexagonal_architecture_example.whoops.application.port.out.WhoopsEventPublisher;
import com.hexagonal_architecture_example.whoops.domain.WhoopsCreatedEvent;

import java.util.ArrayList;
import java.util.List;

public class InMemoryWhoopsEventPublisher implements WhoopsEventPublisher  {
    private final List<WhoopsCreatedEvent> publishedEvents= new ArrayList<>();
    @Override
    public void publish(WhoopsCreatedEvent event) {
        publishedEvents.add(event);
    }

    public List<WhoopsCreatedEvent> getPublishedEvents()
    {
        return List.copyOf(publishedEvents);
    }
}
