package com.hexagonal_architecture_example.whoops.application.port.in;

import com.hexagonal_architecture_example.whoops.domain.user.User;

public interface CreateUserUseCase {

    User create(String userName);

}
