package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Day3 {
    public static void main(String[] args) {
        SpringApplication.run(Day3.class, args);
    }
}

@RestController
@RequestMapping("/greeting")
class GreetingController{
    private final Greeting greeting;

    public GreetingController(Greeting greeting) {
        this.greeting = greeting;
    }

    @GetMapping
    String getGreeting(){
        return greeting.getName();
    }

    @GetMapping("/coffee")
    String getNameCoffee(){
        return greeting.getCoffee();
    }
}

@ConfigurationProperties(prefix = "greeting")
class Greeting{
    private String name;
    private String coffee;

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getCoffee(){
        return coffee;
    }

    public void setCoffee(String coffee){
        this.coffee = coffee;
    }
}
