package com.example.demo;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

//Template Redis
//@SpringBootApplication
//public class Day4 {
//    @Bean
//    public RedisOperations<String, Aircraft> redisOperations(RedisConnectionFactory factory) {
//        Jackson2JsonRedisSerializer<Aircraft> serializer = new Jackson2JsonRedisSerializer<>(Aircraft.class);
//
//        RedisTemplate<String, Aircraft> template = new RedisTemplate<>();
//        template.setConnectionFactory(factory);
//        template.setDefaultSerializer(serializer);
//        template.setKeySerializer(new StringRedisSerializer());
//
//        return template;
//    }
//    public static void main(String[] args) {
//        SpringApplication.run(Day4.class, args);
//    }
//}
//
//@EnableScheduling
//@Component
//class PlaneFinderPoller{
//    private WebClient client = WebClient.create("http://localhost:7634/aircraft");
//    private final RedisConnectionFactory connectionFactory;
//    private final RedisOperations<String, Aircraft> redisOperations;
//
//    PlaneFinderPoller(RedisConnectionFactory connectionFactory,
//                      RedisOperations<String, Aircraft> redisOperations){
//        this.connectionFactory = connectionFactory;
//        this.redisOperations = redisOperations;
//    }
//
//    @Scheduled(fixedRate = 1000)
//    private void pollPlanes(){
//        connectionFactory.getConnection().serverCommands().flushDb();
//
//        client.get()
//                .retrieve()
//                .bodyToFlux(Aircraft.class)
//                .filter(plane -> !plane.getReg().isEmpty())
//                .toString();
////                .formatted(ac ->
////                        redisOperations.opsForValue().set(ac.getReg(), ac));
//
//        redisOperations.opsForValue()
//                .getOperations()
//                .keys("*")
//                .forEach(ac ->
//                        System.out.println(redisOperations.opsForValue().get(ac)));
//    }
//}

//Respository Redis
//@SpringBootApplication
//public class Day4 {
//    public static void main(String[] args) {
//        SpringApplication.run(Day4.class, args);
//    }
//}
//
//@EnableScheduling
//@Component
//class PlaneFinderPoller{
//    private WebClient client = WebClient.create("http://localhost:7634/aircraft");
//    private final RedisConnectionFactory connectionFactory;
//    private final AircraftRepository repository;
//
//    PlaneFinderPoller(RedisConnectionFactory connectionFactory, AircraftRepository repository){
//        this.connectionFactory = connectionFactory;
//        this.repository = repository;
//    }
//
//    @Scheduled(fixedRate = 1000)
//    private void pollPlanes(){
//        connectionFactory.getConnection().serverCommands().flushDb();
//
//        client.get()
//                .retrieve()
//                .bodyToFlux(Aircraft.class)
//                .filter(plane -> !plane.getReg().isEmpty())
//                        .toStream()
//                                .forEach(repository::save);
//
//        repository.findAll().forEach(System.out::println);
//    }
//}

//JPA Repository
@SpringBootApplication
public class Day4 {
    public static void main(String[] args) {
        SpringApplication.run(Day4.class, args);
    }
}

@EnableScheduling
@Component
@RequiredArgsConstructor
class PlaneFinderPoller{
    @NonNull
    private final AircraftRepository repository;
    private WebClient client = WebClient.create("http://localhost:7634/aircraft");

    PlaneFinderPoller(AircraftRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedRate = 1000)
    private void pollPlanes(){
        repository.deleteAll();
        client.get()
                .retrieve()
                .bodyToFlux(Aircraft.class)
                .filter(plane -> !plane.getReg().isEmpty())
                .toStream()
                .forEach(repository::save);
        repository.findAll().forEach(System.out::println);
    }
}