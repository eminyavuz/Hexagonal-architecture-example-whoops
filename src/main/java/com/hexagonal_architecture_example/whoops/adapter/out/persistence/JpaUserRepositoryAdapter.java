package com.hexagonal_architecture_example.whoops.adapter.out.persistence;

import com.hexagonal_architecture_example.whoops.application.port.out.UserRepository;
import com.hexagonal_architecture_example.whoops.domain.user.User;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

public class JpaUserRepositoryAdapter implements UserRepository {

    private  final SpringDataUserRepository repository;

    public JpaUserRepositoryAdapter(SpringDataUserRepository repository)
    {
        this.repository=repository;
    }
    @Override
    public User save(User user) {
        UserJpaEntity entity= new UserJpaEntity(user.getId(),user.getUserName(),user.getCreatedAt());
       UserJpaEntity savedEntity = repository.save(entity);
        return  new User(savedEntity.getId(),savedEntity.getUserName(),savedEntity.getCreatedAt());
    }

    @Override
    public Optional<User> findById(UUID usrId) {
       return repository.findById(usrId)
               .map(entity-> new User(entity.getId(),entity.getUserName(),entity.getCreatedAt()));
    }

    @Override
    public boolean existByUsername(String userName)
    {
        return repository.existsByUserName(userName);
    }

}
