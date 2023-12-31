package com.example.demo;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.io.IOException;

@AllArgsConstructor
@Configuration
public class PositionRetriever {
    private final AircraftRepository repo;
    private final WebSocketHandler handler;
    private final WebClient client;

    public PositionRetriever(AircraftRepository repo, WebSocketHandler handler, WebClient client) {
        this.repo = repo;
        this.handler = handler;
        this.client = client;
    }

    @Bean
    Iterable<Aircraft> retrieveAircraftPositions(String endpoint) {
        repo.deleteAll();

        client.get()
                .uri((null != endpoint) ? endpoint:"")
                .retrieve()
                .bodyToFlux(Aircraft.class)
                .filter(ac -> !ac.getReg().isEmpty())
                .toStream()
                .forEach(repo::save);

        return repo.findAll();
    }

}
