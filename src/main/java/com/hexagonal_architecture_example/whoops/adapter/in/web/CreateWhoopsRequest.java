package com.hexagonal_architecture_example.whoops.adapter.in.web;


import java.util.UUID;

public record CreateWhoopsRequest(UUID userId,String message) {


}
