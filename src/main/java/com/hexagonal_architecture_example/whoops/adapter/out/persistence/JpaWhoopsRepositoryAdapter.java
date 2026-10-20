package com.hexagonal_architecture_example.whoops.adapter.out.persistence;

import com.hexagonal_architecture_example.whoops.application.port.out.WhoopsRepository;
import com.hexagonal_architecture_example.whoops.domain.Whoops;

public class  JpaWhoopsRepositoryAdapter implements WhoopsRepository {
    private final  SpringDataWhoopsRepository repository;
    public JpaWhoopsRepositoryAdapter(SpringDataWhoopsRepository repository)
    {
        this.repository=repository;

    }

    @Override
    public Whoops save(Whoops whoops) {
        WhoopsJpaEntity entity= new WhoopsJpaEntity(whoops.getId(),whoops.getUserId(),whoops.getMessage(),whoops.getCreatedAt());
    repository.save(entity);
    return whoops;
    }
}
