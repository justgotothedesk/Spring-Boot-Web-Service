package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class Day1 {
    public static void main(String[] args) {
        SpringApplication.run(Day1.class, args);
    }
}

class Coffee {
    private final String id;
    private String name;

    public Coffee(String id, String name){
        this.id = id;
        this.name = name;
    }

    public Coffee(String name){
        this(UUID.randomUUID().toString(), name);
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}

@RestController
class RestApiDemoController{
    private List<Coffee> coffees = new ArrayList<>();

    public RestApiDemoController(){
        coffees.addAll(List.of(
                new Coffee("Cafe Cereza"),
                new Coffee("Cafe Ganador"),
                new Coffee("Cafe Lareno"),
                new Coffee("Cafe Tres Pontas")
        ));
    }

    @GetMapping("/coffees")
    Iterable<Coffee> getCoffees(){
        return coffees;
    }

    @GetMapping("/coffees/{id}")
    Optional<Coffee> getCoffeeById(@PathVariable String id){
        for(Coffee c: coffees){
            if (c.getId().equals(id)){
                return Optional.of(c);
            }
        }
        return Optional.empty();
    }

    @PostMapping("/coffees")
    Coffee postCoffee(@RequestBody Coffee coffee){
        coffees.add(coffee);
        return coffee;
    }

//    @PutMapping("/coffees/{id}")
//    Coffee putCoffee(@PathVariable String id, @RequestBody Coffee coffee){
//        int index = -1;
//
//        for(Coffee c: coffees){
//            if(c.getId().equals(id)){
//                index = coffees.indexOf(c);
//                coffees.set(index, coffee);
//            }
//        }
//        return (index == -1)?postCoffee(coffee):coffee;
//    }

    @PutMapping("/{id}")
    ResponseEntity<Coffee> putCoffee(@PathVariable String id, @RequestBody Coffee coffee){
        int index = -1;
        for(Coffee c: coffees){
            if(c.getId().equals(id)){
                index = coffees.indexOf(c);
                coffees.set(index, coffee);
            }
        }
        return (index == -1)?
                new ResponseEntity<>(postCoffee(coffee), HttpStatus.CREATED):
                new ResponseEntity<>(coffee, HttpStatus.OK);
    }

    @DeleteMapping("/coffees/{id}")
    void deleteCoffee(@PathVariable String id){
        coffees.removeIf(c -> c.getId().equals(id));
    }
}