package com.hexagonal_architecture_example.whoops.adapter.out.persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SpringDataUserRepository extends JpaRepository<UserJpaEntity, UUID> {
    boolean existsByUserName(String userName);
}
