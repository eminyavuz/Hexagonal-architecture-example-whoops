package com.hexagonal_architecture_example.whoops.adapter.in.web;

import com.hexagonal_architecture_example.whoops.adapter.in.web.dto.CreateUserRequest;
import com.hexagonal_architecture_example.whoops.adapter.in.web.dto.UserResponse;
import com.hexagonal_architecture_example.whoops.application.port.in.CreateUserUseCase;
import com.hexagonal_architecture_example.whoops.domain.user.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {

    private final CreateUserUseCase useCase;
    public UserController(CreateUserUseCase useCase)
    {
        this.useCase=useCase;
    }

    @PostMapping
    public ResponseEntity<UserResponse> create( @Valid @RequestBody CreateUserRequest req)
    {
        User user=useCase.create(req.userName());
        UserResponse userResponse= new UserResponse(user.getId(),user.getUserName(), user.getCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

}
