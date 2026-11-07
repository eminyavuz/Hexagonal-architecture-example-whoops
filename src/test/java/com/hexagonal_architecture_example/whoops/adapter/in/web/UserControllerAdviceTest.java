package com.hexagonal_architecture_example.whoops.adapter.in.web;

import com.hexagonal_architecture_example.whoops.application.port.in.ConnectedPlatformUseCase;
import com.hexagonal_architecture_example.whoops.application.port.in.CreateUserUseCase;
import com.hexagonal_architecture_example.whoops.application.port.in.GetConnectedPlatformsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerAdviceTest {

    private MockMvc mockMvc;
    private CreateUserUseCase useCase;
    private ConnectedPlatformUseCase platformUseCase;
    private GetConnectedPlatformsUseCase getConnectedPlatformsUseCase;


    @BeforeEach
    void setUp() {
        useCase = mock(CreateUserUseCase.class);
        platformUseCase = mock(ConnectedPlatformUseCase.class);
        UserController controller = new UserController(useCase, platformUseCase,getConnectedPlatformsUseCase);

        UserControllerAdvice advice = new UserControllerAdvice();

        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .setControllerAdvice(advice)
                .build();
    }

    @Test
    void shouldReturnConflictWhenUsernameAlreadyExists() throws Exception {
        when(useCase.create("Dwight"))
                .thenThrow(new IllegalArgumentException("Username Already Exist"));

        String json = """
                {
                    "userName": "Dwight"
                }
                """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message").value("Username Already Exist"));

        verify(useCase, times(1)).create("Dwight");
    }

    @Test
    void shouldReturnInternalServerErrorWhenUnexpectedExceptionOccurs() throws Exception {
        when(useCase.create("Dwight"))
                .thenThrow(new RuntimeException("Unexpected error"));

        String json = """
                {
                    "userName": "Dwight"
                }
                """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Internal Server Error"));

        verify(useCase, times(1)).create("Dwight");
    }
}