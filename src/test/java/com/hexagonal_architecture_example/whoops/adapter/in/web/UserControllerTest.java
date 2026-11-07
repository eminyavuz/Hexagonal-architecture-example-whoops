package com.hexagonal_architecture_example.whoops.adapter.in.web;

import com.hexagonal_architecture_example.whoops.application.port.in.ConnectedPlatformUseCase;
import com.hexagonal_architecture_example.whoops.application.port.in.CreateUserUseCase;
import com.hexagonal_architecture_example.whoops.application.port.in.GetConnectedPlatformsUseCase;
import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;
import com.hexagonal_architecture_example.whoops.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {
    private CreateUserUseCase useCase;
    private ConnectedPlatformUseCase platformUseCase;
    private GetConnectedPlatformsUseCase getConnectedPlatformsUseCase;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        useCase = mock(CreateUserUseCase.class);
        platformUseCase=mock(ConnectedPlatformUseCase.class);
        getConnectedPlatformsUseCase=mock(GetConnectedPlatformsUseCase.class);
        UserController controller = new UserController(useCase,platformUseCase,getConnectedPlatformsUseCase);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    void shouldReturnConnectedPlatforms() throws Exception {
        UUID userId = UUID.randomUUID();

        Set<SocialMediaPlatform> platforms = Set.of(
                SocialMediaPlatform.INSTAGRAM,
                SocialMediaPlatform.WHATSAPP
        );

        when(getConnectedPlatformsUseCase.getConnectedPlatforms(userId))
                .thenReturn(platforms);

        mockMvc.perform(get("/api/users/{userId}/platforms", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$", containsInAnyOrder(
                        "INSTAGRAM", "WHATSAPP"
                )));
    }
    @Test
    void shouldConnectPlatform() throws Exception {
        UUID userId = UUID.randomUUID();

        String json = """
            {
                "platform": "INSTAGRAM"
            }
            """;

        mockMvc.perform(post("/api/users/{userId}/platforms", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        verify(platformUseCase).connect(userId, SocialMediaPlatform.INSTAGRAM);
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
