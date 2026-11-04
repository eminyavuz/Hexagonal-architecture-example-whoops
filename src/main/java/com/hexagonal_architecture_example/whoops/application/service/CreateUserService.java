package com.hexagonal_architecture_example.whoops.application.service;

import com.hexagonal_architecture_example.whoops.application.port.in.CreateUserUseCase;
import com.hexagonal_architecture_example.whoops.application.port.out.UserRepository;
import com.hexagonal_architecture_example.whoops.domain.user.User;

public class CreateUserService  implements CreateUserUseCase {
   private final UserRepository userRepository;

   public CreateUserService(UserRepository repository)
   {
       this.userRepository=repository;
   }
    @Override
    public User create(String userName) {
       if(userRepository.existByUsername(userName.trim()))
           throw  new IllegalArgumentException("Username Already Exist");
       User user=User.create(userName);

       return  userRepository.save(user);
    }
}
