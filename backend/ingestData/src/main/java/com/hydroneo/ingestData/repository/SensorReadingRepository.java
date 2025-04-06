package com.hydroneo.ingestData.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.hydroneo.ingestData.models.SensorReading;

import reactor.core.publisher.Flux;

public interface SensorReadingRepository extends ReactiveMongoRepository<SensorReading, String>  {
    Flux<SensorReading> findByStation(String station);
}
