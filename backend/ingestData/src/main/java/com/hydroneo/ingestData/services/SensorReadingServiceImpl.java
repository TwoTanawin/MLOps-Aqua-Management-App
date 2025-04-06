package com.hydroneo.ingestData.services;

import org.springframework.stereotype.Service;

import com.hydroneo.ingestData.models.SensorReading;
import com.hydroneo.ingestData.repository.SensorReadingRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SensorReadingServiceImpl implements SensorReadingService {
    
    private final SensorReadingRepository repository;

    public SensorReadingServiceImpl(SensorReadingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Mono<SensorReading> saveReading(SensorReading reading) {
        return repository.save(reading);
    }

    @Override
    public Flux<SensorReading> getAllReadings() {
        return repository.findAll();
    }

    @Override
    public Flux<SensorReading> getReadingsByStation(String station) {
        return repository.findByStation(station);
    }
}
