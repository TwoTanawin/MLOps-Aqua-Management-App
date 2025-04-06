package com.hydroneo.aquaCore.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.hydroneo.aquaCore.models.Pond;

import reactor.core.publisher.Flux;

public interface PondRepository extends ReactiveCrudRepository<Pond, UUID> {
    Flux<Pond> findByUserId(UUID userId);
}
