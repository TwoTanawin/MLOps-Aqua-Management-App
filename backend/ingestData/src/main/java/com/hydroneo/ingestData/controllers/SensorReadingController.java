package com.hydroneo.ingestData.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hydroneo.ingestData.models.SensorReading;
import com.hydroneo.ingestData.services.SensorReadingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/readings")
@Tag(name = "Sensor Readings", description = "Endpoints for ingesting and retrieving sensor data")
public class SensorReadingController {
    private final SensorReadingService service;

    public SensorReadingController(SensorReadingService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
        summary = "Save sensor reading",
        description = "Ingest a new sensor reading document into MongoDB"
    )
    public Mono<SensorReading> saveReading(@RequestBody SensorReading reading) {
        return service.saveReading(reading);
    }

    @GetMapping
    @Operation(
        summary = "Get all sensor readings",
        description = "Retrieve all sensor readings from the database"
    )
    public Flux<SensorReading> getAll() {
        return service.getAllReadings();
    }

    @GetMapping("/station/{station}")
    @Operation(
        summary = "Get sensor readings by station",
        description = "Retrieve all readings from a specific station"
    )
    public Flux<SensorReading> getByStation(@PathVariable String station) {
        return service.getReadingsByStation(station);
    }
}
