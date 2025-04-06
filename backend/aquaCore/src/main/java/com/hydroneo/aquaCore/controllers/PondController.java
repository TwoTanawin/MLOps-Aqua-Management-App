package com.hydroneo.aquaCore.controllers;

import java.util.UUID;

// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hydroneo.aquaCore.models.Pond;
import com.hydroneo.aquaCore.repository.PondRepository;
import com.hydroneo.aquaCore.services.PondService;
import com.hydroneo.aquaCore.services.UserService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/ponds")
@Tag(name = "Ponds", description = "Endpoints for managing ponds")
public class PondController {

    private final PondService pondService;
    private final UserService userService;
    private final PondRepository pondRepository;

    public PondController(PondService pondService, UserService userService, PondRepository pondRepository) {
        this.pondService = pondService;
        this.userService = userService;
        this.pondRepository = pondRepository;
    }

    @GetMapping
    @Operation(
            summary = "Get all ponds",
            description = "Retrieve all pond records from the database"
    )
    public Flux<Pond> getAllPonds() {
        return pondService.getAllPonds();
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get pond by ID",
            description = "Retrieve a pond by its unique ID"
    )
    public Mono<Pond> getPondById(@PathVariable UUID id) {
        return pondService.getPondById(id);
    }

    @GetMapping("/user/{userId}")
    @Operation(
            summary = "Get ponds by user ID",
            description = "Retrieve all ponds associated with a specific user"
    )
    public Flux<Pond> getPondsByUserId(@PathVariable UUID userId) {
        return pondService.getPondsByUserId(userId);
    }

    @PostMapping
    @Operation(
            summary = "Create a new pond",
            description = "Create and store a new pond record in the database"
    )
    public Mono<Pond> createPond(@RequestBody Pond pond) {
        return userService.getUserById(pond.getUserId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("User not found")))
                .flatMap(user -> pondRepository.save(pond));
    }    

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a pond",
            description = "Remove a pond from the database using its unique ID"
    )
    public Mono<Void> deletePond(@PathVariable UUID id) {
        return pondService.deletePond(id);
    }
}

