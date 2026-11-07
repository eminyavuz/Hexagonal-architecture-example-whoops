package com.hexagonal_architecture_example.whoops.adapter.in.web;

import com.hexagonal_architecture_example.whoops.application.port.in.CreateUserUseCase;
import com.hexagonal_architecture_example.whoops.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {
    private CreateUserUseCase useCase;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        useCase = mock(CreateUserUseCase.class);
        UserController controller = new UserController(useCase);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    public void shouldCreateUser() throws Exception {
        User user = User.create("Dwight");
        when(useCase.create(user.getUserName())).thenReturn(user);

        String json = """
                { "userName":"Dwight"}
                """;
        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value("Dwight")).
                andExpect(jsonPath("$.createdDate").exists());

        verify(useCase).create("Dwight");


    }


    @Test
    void shouldReturnBadRequestWhenJsonIsInvalid() throws Exception {
        String invalidJson = """
            {
                "userName": "Dwight"
            """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(useCase);
    }


    @Test
    void shouldReturnBadRequestWhenUsernameIsBlank() throws Exception {
        String json = """
            {
                "userName": ""
            }
            """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(useCase);
    }

    @Test
    void shouldPassUsernameToUseCase() throws Exception {
        User user = User.create("Dwight");

        when(useCase.create("Dwight")).thenReturn(user);

        String json = """
            {
                "userName": "Dwight"
            }
            """;

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(useCase, times(1)).create("Dwight");
    }
}
