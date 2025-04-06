package com.hydroneo.ingestData.services;

import com.hydroneo.ingestData.models.SensorReading;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SensorReadingService {
    Mono<SensorReading> saveReading(SensorReading reading);
    Flux<SensorReading> getAllReadings();
    Flux<SensorReading> getReadingsByStation(String station);

}
