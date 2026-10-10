package com.hexagonal_architecture_example.whoops.adapter.out.persistence;

import com.hexagonal_architecture_example.whoops.application.port.out.WhoopsRepository;
import com.hexagonal_architecture_example.whoops.domain.Whoops;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class InMemoryWhoopsRepository implements WhoopsRepository {

    private final Map<UUID,Whoops> records= new HashMap<>();
    @Override
    public Whoops save(Whoops whops) {
        records.put(whops.getId(),whops);
        return whops;
    }

    public Optional<Whoops> findById(UUID id)
    {
        if (id == null) {
            throw new IllegalArgumentException("Whoops id cannot be null");
        }
        return Optional.ofNullable(records.get(id));

    }
}
