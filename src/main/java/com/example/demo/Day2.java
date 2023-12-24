// Day 2 : JPA Entity 설정 및 Day 1에서의 CRUD 매핑 기능 간단하게 줄이기
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootApplication
public class Day2 {
    public static void main(String[] args) {
        SpringApplication.run(Day2.class, args);
    }
}

interface CoffeeRepository extends CrudRepository<Coffee2, String>{}

@Entity
class Coffee2 {
    @Id
    private final String id;
    private String name;

    public Coffee2(String id, String name){
        this.id = id;
        this.name = name;
    }

    public Coffee2() {
        this.id = UUID.randomUUID().toString();
    }

    public Coffee2(String name){
        this(); // 기본 생성자 호출
        this.name = name;
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
@RequestMapping("/coffees2")
class RestApiDemoController2{
    private final CoffeeRepository coffeeRepository;

    public RestApiDemoController2(CoffeeRepository coffeeRepository){
        this.coffeeRepository = coffeeRepository;

        this.coffeeRepository.saveAll(List.of(
                new Coffee2("Cafe Cereza"),
                new Coffee2("Cafe Ganador"),
                new Coffee2("Cafe Lareno"),
                new Coffee2("Cafe Tres Pontas")
        ));
    }

    @GetMapping
    Iterable<Coffee2> getCoffees(){
        return coffeeRepository.findAll();
    }

    @GetMapping("/{id}")
    Optional<Coffee2> getCoffeeById(@PathVariable String id){
        return coffeeRepository.findById(id);
    }

    @PostMapping
    Coffee2 postCoffee(@RequestBody Coffee2 coffee){
        return coffeeRepository.save(coffee);
    }

    @PutMapping("/{id}")
    ResponseEntity<Coffee2> putCoffee(@PathVariable String id, @RequestBody Coffee2 coffee){
        return (!coffeeRepository.existsById(id))?
                new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.CREATED):
                new ResponseEntity<>(coffeeRepository.save(coffee), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    void deleteCoffee(@PathVariable String id){
        coffeeRepository.deleteById(id);
    }
}