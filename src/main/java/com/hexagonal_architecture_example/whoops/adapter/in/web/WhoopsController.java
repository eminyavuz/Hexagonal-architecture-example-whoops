package com.hexagonal_architecture_example.whoops.adapter.in.web;

import com.hexagonal_architecture_example.whoops.application.port.in.CreateWhoopUseCase;
import com.hexagonal_architecture_example.whoops.domain.Whoops;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class WhoopsController  {
    private final CreateWhoopUseCase createUseCase;

 public  WhoopsController(CreateWhoopUseCase createWhoopUseCase){
        this.createUseCase= createWhoopUseCase;
    }

   @PostMapping("/create")

    public Whoops createWhoops(@RequestBody CreateWhoopsRequest req)
   {
return createUseCase.create(req.userId(),req.message());
   }


}
