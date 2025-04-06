package com.hydroneo.aquaCore.services;

import java.util.UUID;

import com.hydroneo.aquaCore.models.Pond;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PondService {
    public Flux<Pond> getAllPonds();
    public Mono<Pond> getPondById(UUID id);
    public Flux<Pond> getPondsByUserId(UUID userId);
    public Mono<Pond> createPond(Pond pond);
    public Mono<Void> deletePond(UUID id);
}
