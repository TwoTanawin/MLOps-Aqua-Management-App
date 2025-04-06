package com.hydroneo.ingestData.scheduler;

import java.time.Instant;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

// import com.hydroneo.ingestData.models.AxisData;
import com.hydroneo.ingestData.models.PondData;
import com.hydroneo.ingestData.models.SensorReading;
import com.hydroneo.ingestData.services.SensorReadingService;

@Component
public class DataIngestScheduler {
    
    private final SensorReadingService service;

    public DataIngestScheduler(SensorReadingService service) {
        this.service = service;
    }

    // Run every 2 minutes
    @Scheduled(fixedRate = 120000) // 2 minutes = 120000 ms
    public void generateAndIngestData() {
        SensorReading reading = new SensorReading(
                null,
                "AUTO-" + (int)(Math.random() * 100),
                Instant.now().toString(),
                new PondData(
                    Math.random() * 10,
                    Math.random() * 10,
                    Math.random() * 10,
                    Math.random() * 10,
                    true
                )
        );

        service.saveReading(reading)
            .doOnSuccess(r -> System.out.println("✅ Auto-ingested: " + r))
            .subscribe();
    }
}
