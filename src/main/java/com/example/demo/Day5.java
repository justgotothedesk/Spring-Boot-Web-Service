// Day 5 : 스프링 클라우드 스트림 사용 및 supplier, consumer, function
// PositionController.java

package com.example.demo;

import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Hooks;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

@SpringBootApplication
public class Day5 {
    public static void main(String[] args) {
        Hooks.onOperatorDebug();
        SpringApplication.run(Day5.class, args);
    }
}

@AllArgsConstructor
@Configuration
public class PositionReporter {
    private final PlaneFinderService pfService;

    public PositionReporter(PlaneFinderService pfService) {
        this.pfService = pfService;
    }

    @Bean
    Supplier<Iterable<Aircraft>> reportPositions(){
        return () -> {
            try {
                return pfService.getAircraft();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return List.of();
        };
    }
}