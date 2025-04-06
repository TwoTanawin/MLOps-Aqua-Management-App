package com.hydroneo.aquaCore.services;

import java.util.UUID;

import com.hydroneo.aquaCore.models.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    public Flux<User> getAllUsers();
    Mono<User> getUserById(UUID id);
    public Mono<User> createUser(User user);
    public Mono<Void> deleteUser(UUID id);
    public Mono<User> findByEmail(String email);
}
