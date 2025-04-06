package com.hydroneo.ingestData.controllers;

import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class HelloController {
    
    @GetMapping("/")
    public Mono<String> hello(){
        return Mono.just("Hello Ingest Data");
    }
}
