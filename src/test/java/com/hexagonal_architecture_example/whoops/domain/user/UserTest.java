package com.hexagonal_architecture_example.whoops.domain.user;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
     void  shouldCreateUserSuccessfully()
    {
        String userName= "Jim Halpert";
        User user=User.create(userName);

        assertNotNull(user.getId());
        assertEquals(userName,user.getUserName());
        assertNotNull(user.getCreatedAt());
    }

    @Test
    void  shouldThrowErrorWhenUsernameIsNull()
    {
       assertThrows(IllegalArgumentException.class,()->User.create(null));
    }
@Test
    void shouldThrowExceptionWhenUsernameIsBlank()
{
    assertThrows(IllegalArgumentException.class,()->User.create(""));
}
@Test
    void  shouldThrowExceptionWhenUsernameIsOnlyWhiteSpace()
{
    assertThrows(IllegalArgumentException.class,()->User.create(" "));
}
@Test
    void shoulTrimUsername()
{
    User user=  User.create(" Jim Halpert ");
    assertEquals("Jim Halpert",user.getUserName());
}

@Test
    void shouldCreateDifferentId()
{
    User user1= User.create("Dwight Schurte");
    User user2= User.create("Jim Halpert");

    assertNotEquals(user1.getId(),user2.getId());
}
@Test
    void shouldThrowExceptionWhenIdIsNull()
{
    assertThrows(IllegalArgumentException.class,()->new User(null,"Jim Halpert", Instant.now()));
}
@Test
    void shouldThrowExceptionWhenCreatedAtIsNull()
{
    assertThrows(IllegalArgumentException.class,()->new User(UUID.randomUUID(),"Ryan Howard",null));
}
}
