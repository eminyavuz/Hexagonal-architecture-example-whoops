package com.hexagonal_architecture_example.whoops.adapter.in.web;

import com.hexagonal_architecture_example.whoops.adapter.in.web.dto.ConnectPlatformRequest;
import com.hexagonal_architecture_example.whoops.adapter.in.web.dto.CreateUserRequest;
import com.hexagonal_architecture_example.whoops.adapter.in.web.dto.UserResponse;
import com.hexagonal_architecture_example.whoops.application.port.in.ConnectedPlatformUseCase;
import com.hexagonal_architecture_example.whoops.application.port.in.CreateUserUseCase;
import com.hexagonal_architecture_example.whoops.application.port.in.GetConnectedPlatformsUseCase;
import com.hexagonal_architecture_example.whoops.application.service.GetConnectedPlatformsService;
import com.hexagonal_architecture_example.whoops.domain.platform.SocialMediaPlatform;
import com.hexagonal_architecture_example.whoops.domain.user.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CreateUserUseCase useCase;
    private final ConnectedPlatformUseCase platformUseCase;
    private final GetConnectedPlatformsUseCase getConnectedPlatformsUseCase;
    public UserController(CreateUserUseCase useCase, ConnectedPlatformUseCase platformUseCase,GetConnectedPlatformsUseCase getConnectedPlatformsUseCase)
    {
        this.platformUseCase=platformUseCase;
        this.useCase=useCase;
        this.getConnectedPlatformsUseCase=getConnectedPlatformsUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create( @Valid @RequestBody CreateUserRequest req)
    {
        User user=useCase.create(req.userName());
        UserResponse userResponse= new UserResponse(user.getId(),user.getUserName(), user.getCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
    @PostMapping("/{userId}/platforms")
    public ResponseEntity<Void> connectedPlatform(
            @PathVariable UUID userId, @Valid @RequestBody ConnectPlatformRequest req)
    {
        platformUseCase.connect(userId,req.platform());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/{userId}/platforms")
    public ResponseEntity<Set<SocialMediaPlatform>> getConnectedPlatforms(@PathVariable UUID userId)
    {
        return ResponseEntity.ok(getConnectedPlatformsUseCase.getConnectedPlatforms(userId));
    }

}
