package com.hexagonal_architecture_example.whoops.application.port.out;

import com.hexagonal_architecture_example.whoops.domain.WhoopsCreatedEvent;

public interface WhoopsEventPublisher {
    void publish(WhoopsCreatedEvent event);
}
