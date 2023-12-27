package com.example.demo;

import org.springframework.data.repository.CrudRepository;

//Repository Redis
public interface AircraftRepository extends CrudRepository<Aircraft, Long> {
}
