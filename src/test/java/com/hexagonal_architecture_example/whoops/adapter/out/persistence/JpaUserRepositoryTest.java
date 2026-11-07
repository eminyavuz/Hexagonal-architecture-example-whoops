package com.hexagonal_architecture_example.whoops.adapter.out.persistence;

import com.hexagonal_architecture_example.whoops.application.port.out.UserRepository;
import com.hexagonal_architecture_example.whoops.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@Import(JpaUserRepositoryAdapter.class)
class JpaUserRepositoryTest {

    @Container
    static PostgreSQLContainer postgres =
            new PostgreSQLContainer("postgres:17")
                    .withDatabaseName("whoops_test")
                    .withUsername("test")
                    .withPassword("test");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldStartContainer() {
        assertTrue(postgres.isRunning());
    }
    @Test
    void shouldSavedUser()
    {
        User user= User.create("Jim Halpert");
        User savedUser= userRepository.save(user);
       assertNotNull(user);
       assertEquals(user.getId(),savedUser.getId());
    }
    @Test
    void shouldFindUserById()
    {
        User user= User.create("Jim");
       User savedUser=userRepository.save(user);

        Optional<User> found=userRepository.findById(savedUser.getId());

        assertTrue(found.isPresent());
        assertEquals(savedUser.getId(),found.get().getId());
    }
@Test
    void shouldReturnEmptyWhenUserNotFound()
{
    UUID id= UUID.randomUUID();
    Optional<User>user= userRepository.findById(id);

    assertTrue(user.isEmpty());
}
@Test
    void shouldReturnTrueWhenUsernameExists()
{
    User user= User.create("Jim");
    userRepository.save(user);
  boolean found= userRepository.existByUsername("Jim");
  assertTrue(found);
}

@Test
    void shouldReturnFalseWhenUsernameDoesNotExist()
{
    boolean found= userRepository.existByUsername("Dwight");
    assertFalse(found);
}

}