package com.hexagonal_architecture_example.whoops.adapter.in.web;

import com.hexagonal_architecture_example.whoops.application.port.in.CreateWhoopUseCase;
import com.hexagonal_architecture_example.whoops.domain.Whoops;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import java.time.Instant;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import java.util.UUID;

import static org.mockito.Mockito.*;


public class WhoopsControllerTest {
    private CreateWhoopUseCase createWhoopUseCase;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp()
    {
         createWhoopUseCase=mock(CreateWhoopUseCase.class);
        WhoopsController controller= new WhoopsController(createWhoopUseCase);

        mockMvc= MockMvcBuilders.standaloneSetup(controller).build();
    }
@Test
    void shouldCreateWhoops() throws Exception
{
    UUID userId= UUID.randomUUID();
    UUID whoopsId= UUID.randomUUID();
    String message="There is a whoop to reach you";
    Instant createdAt=Instant.now();

    Whoops whoops= Whoops.reconstitute(whoopsId,userId,message,createdAt);

    when(createWhoopUseCase.create(userId,message)).thenReturn(whoops);

    String json= """
               {
                        "userId": "%s",
                        "message": "%s"
                    }
               
            """.formatted(userId,message);
    mockMvc.perform(post("/api/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
    )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(message))
            .andExpect(jsonPath("$.userId").value(userId.toString()))
            .andExpect(jsonPath("$.id").value(whoopsId.toString()));
    verify(createWhoopUseCase).create(userId,message);

}

}
