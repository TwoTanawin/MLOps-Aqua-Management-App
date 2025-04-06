package com.hydroneo.aquaCore.services;

import java.util.UUID;

import com.hydroneo.aquaCore.models.User;
import org.springframework.stereotype.Service;

import com.hydroneo.aquaCore.models.Pond;
import com.hydroneo.aquaCore.repository.PondRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PondServiceImpl implements PondService {

    private final PondRepository pondRepository;

    private final UserService userService;

    public PondServiceImpl(PondRepository pondRepository, UserService userService){
        this.pondRepository = pondRepository;
        this.userService = userService;
    }

    @Override
    public Flux<Pond> getAllPonds() {
        return pondRepository.findAll();
    }

    @Override
    public Mono<Pond> getPondById(UUID id) {
        return pondRepository.findById(id);
    }

    @Override
    public Flux<Pond> getPondsByUserId(UUID userId) {
        return pondRepository.findByUserId(userId);
    }

    @Override
    public Mono<Pond> createPond(Pond pond) {
        return userService.getUserById(pond.getUserId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User not found")))
                .flatMap(user -> pondRepository.save(pond));
    }

    @Override
    public Mono<Void> deletePond(UUID id) {
        return pondRepository.deleteById(id);
    }
}
