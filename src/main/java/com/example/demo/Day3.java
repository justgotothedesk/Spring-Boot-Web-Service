// Day3 : configuration 속성 사용 및 Actuator 사용법 익히기

package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.ranges.DocumentRange;

@SpringBootApplication
@ConfigurationPropertiesScan
public class Day3 {
    public static void main(String[] args) {
        SpringApplication.run(Day3.class, args);
    }

    @Bean
    @ConfigurationProperties(prefix = "droid")
    Droid createDroid(){
        return new Droid();
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

class Droid{
    private String id, description;

    public String getId(){
        return id;
    }

    public String getDescription(){
        return description;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setDescription(String description){
        this.description = description;
    }
}

@RestController
@RequestMapping("/droid")
class DroidController{
    private final Droid droid;

    public DroidController(Droid droid){
        this.droid = droid;
    }

    @GetMapping
    Droid getDroid(){
        return droid;
    }
}
