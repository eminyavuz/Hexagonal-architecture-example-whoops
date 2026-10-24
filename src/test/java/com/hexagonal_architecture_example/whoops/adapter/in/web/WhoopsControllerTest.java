package com.hexagonal_architecture_example.whoops.adapter.in.web;

import com.hexagonal_architecture_example.whoops.application.port.in.CreateWhoopUseCase;
import com.hexagonal_architecture_example.whoops.domain.SocialMediaPlatform;
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
    String senderName="Pam Beesly";
    SocialMediaPlatform sourcePlatform=SocialMediaPlatform.INSTAGRAM;
    Instant createdAt=Instant.now();

    Whoops whoops= Whoops.reconstitute(whoopsId,userId,senderName,sourcePlatform,message,createdAt);

    when(createWhoopUseCase.create(userId,senderName,sourcePlatform,message)).thenReturn(whoops);

    String json= """
               {
                        "userId": "%s",
                        "message": "%s",
                        "senderName":"%s",
                        "sourcePlatform":"%s"
                    }
               
            """.formatted(userId,message,senderName,sourcePlatform);
    mockMvc.perform(post("/api/whoops")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json)
    )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value(message))
            .andExpect(jsonPath("$.userId").value(userId.toString()))
            .andExpect(jsonPath("$.id").value(whoopsId.toString()))
                    .andExpect(jsonPath("$.senderName").value(senderName))
                            .andExpect(jsonPath("$.sourcePlatform").value(sourcePlatform.name()));


    verify(createWhoopUseCase).create(userId,senderName,sourcePlatform,message);

}

}
