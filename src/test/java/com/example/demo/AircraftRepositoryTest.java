package com.example.demo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class AircraftRepositoryTest {

    @Autowired
    private AircraftRepository repository;

    private Aircraft ac1, ac2;

    @BeforeEach
    void setUp() {
        ac1 = new Aircraft(1L, "SAL001", "sqwk", "N12345", "SAL001",
                "STL-SF0", "LJ", "ct",
                30000, 280, 440, 0, 0,
                39.2979849, -94.71921, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        ac2 = new Aircraft(1L, "SAL001", "sqwk", "N12345", "SAL001",
                "STL-SF0", "LJ", "ct",
                30000, 280, 440, 0, 0,
                39.2979849, -94.71921, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void testFindAll() {
        assertEquals(Optional.of(ac1), repository.findById(ac1.getId()));
        assertEquals(Optional.of(ac1), repository.findById(ac2.getId()));
    }
}
