package com.hexagonal_architecture_example.whoops.application.service;

import com.hexagonal_architecture_example.whoops.application.port.out.UserRepository;
import com.hexagonal_architecture_example.whoops.domain.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CreateUserServiceTest {
    UserRepository repository= mock(UserRepository.class);
    CreateUserService service=  new CreateUserService(repository);
    @Test
    void shouldCreateUser()
    {

       when(repository.existByUsername("Jim Halpert"))
               .thenReturn(false);
       when(repository.save(any(User.class)))
               .thenAnswer(invocation->invocation.getArgument(0));

       User result= service.create("Jim Halpert");

       assertNotNull(result);
       assertEquals("Jim Halpert",result.getUserName());
       verify(repository,times(1)).save(any(User.class));

    }
    @Test
    void shouldThrowExceptionWhenUsernameIsAlreadyExist()
    {
        when(repository.existByUsername("Jim Halpert"))
                .thenReturn(true);
        assertThrows(IllegalArgumentException.class,()->service.create("Jim Halpert"));

        verify(repository,never()).save(any(User.class));
    }

    @Test
     void shouldTrimUserName()
    {
        when(repository.existByUsername("Jim Halpert"))
                .thenReturn(false);

        when(repository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        User user= service.create(" Jim Halpert ");
        assertEquals("Jim Halpert",user.getUserName());

        verify(repository).existByUsername("Jim Halpert");
        verify(repository,times(1)).save(any(User.class));
    }

}
