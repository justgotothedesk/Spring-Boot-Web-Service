package com.example.demo;

import org.apache.catalina.core.ApplicationContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WebFluxTest(controllers = {PositionControllerTest.class})
public class PositionControllerTest {
    @Autowired
    private WebTestClient client;

    private Aircraft ac1, ac2, ac3;

    @MockBean
    private PositionRetriever retriever;

    @MockBean
    private AircraftRepository repository;

    @MockBean
    private PositionService service;

    @MockBean
    private RSocketRequester requester;

    @BeforeEach
    void setUp(ApplicationContext context) {
        ac1 = new Aircraft(1L, "SAL001", "sqwk", "N12345", "SAL001",
                "STL-SF0", "LJ", "ct",
                30000, 280, 440, 0, 0,
                39.2979849, -94.71921, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        ac2 = new Aircraft(2L, "SAL001", "sqwk", "N54321", "SAL001",
                "STL-SF0", "LJ", "ct",
                30000, 280, 440, 0, 0,
                39.2979849, -94.71921, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        ac3 = new Aircraft(3L, "SAL001", "sqwk", "N54321", "SAL001",
                "STL-SF0", "LJ", "ct",
                30000, 280, 440, 0, 0,
                39.2979849, -94.71921, 0D, 0D, 0D,
                true, false,
                Instant.now(), Instant.now(), Instant.now());

        //Mockito.when(service.getAllAircraft()).thenReturn(List.of(ac1, ac2, ac3));
        Hooks.onOperatorDebug();
        Mockito.when(service.getAllAircraft()).thenReturn(
                Flux.just(ac1, ac2, ac3)
                        .concatWith(Flux.error(new Throwable("Bad position report")))
        );
        Mockito.when(service.getAircraftById(1L)).thenReturn(Mono.just(ac1));
        Mockito.when(service.getAircraftById(2L)).thenReturn(Mono.just(ac2));
        Mockito.when(service.getAircraftById(3L)).thenReturn(Mono.just(ac3));
        Mockito.when(service.getAircraftByReg("N12345"))
                .thenReturn(Flux.just(ac1));
        Mockito.when(service.getAircraftByReg("N54321"))
                .thenReturn(Flux.just(ac2, ac3));
    }
    @AfterEach
    void tearDown() {

    }
    @Test
    void getCurrentAircraftPositions(@Autowired WebTestClient client) {
        final Iterable&lt;Aircraft&gt; acPostions = client.get()
                .uri("/aircraft")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Aircraft.class)
                .returnResult()
                .getResponseBody();

        assertEquals(List.of(ac1, ac2), acPositions)
    }

    @Test
    void getCurrentACPositions() {
        StepVerifier.create(client.get()
                .uri("/acpos")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Aircraft.class)
                .getResponseBody())
                .expectNext(ac1)
                .expectNext(ac2)
                .expectNext(ac3)
                .verifyComplete();
    }

    @Test
    void searchForACPositionById() {
        StepVerifier.create(client.get()
                .uri("/acpos/search?id=1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Aircraft.class)
                .getResponseBody())
                .expectNext(ac1)
                .verifyComplete();
    }

    @Test
    void searchForACPositionByReg() {
        StepVerifier.create(client.get()
                .uri("/acpos/search?reg=N54321")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .returnResult(Aircraft.class)
                .getResponseBody())
                .expectNext(ac2)
                .expectNext(ac3)
                .verifyComplete();
    }
}
