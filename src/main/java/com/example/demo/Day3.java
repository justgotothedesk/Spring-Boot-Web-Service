package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Day3 {
    public static void main(String[] args) {
        SpringApplication.run(Day3.class, args);
    }
}

@RestController
@RequestMapping("/greeting")
class GreetingController{
    @Value("${greeting-name : Mirage}")
    private String name;

    @Value("${greeting-coffee : ${greeting-name} is drinking Cafe Ganador}")
    private String coffee;

    @GetMapping
    String getGreeting(){
        return name;
    }

    @GetMapping("/coffee")
    String getNameCoffee(){
        return coffee;
    }
}

