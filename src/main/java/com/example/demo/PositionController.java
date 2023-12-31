package com.example.demo;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RequiredArgsConstructor
//@Controller
//public class PositionController {
//    private final AircraftRepository repository;
//    private final RSocketRequester requester;
//    private WebClient client = WebClient.create("http://localhost:7634/aircraft");
//
//    public PositionController(AircraftRepository repository, RSocketRequester requester) {
//        this.repository = repository;
//        this.requester = RSocketRequester.builder().tcp("localhost", 7635);
//    }
//
//    @GetMapping("/aircraft")
//    public String getCurrentAircraftPositions(Model model) {
//        Flux<Aircraft> aircraftFlux = repository.deleteAll()
//                        .thenMany(client.get()
//                                .retrieve()
//                                .bodyToFlux(Aircraft.class)
//                                .filter(plane -> !plane.getReg().isEmpty())
//                                .flatMap(repository::save));
//
//        model.addAttribute("currentPositions", aircraftFlux);
//        return "positions";
//    }
//
//    @ResponseBody
//    @GetMapping(value = "/acstream",
//               produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<Aircraft> getCurrentACPositionsStream() {
//        return requester.route("acstream")
//                .data("Requesting aircraft positions")
//                .retrieveFlux(Aircraft.class);
//    }
//}

@AllArgsConstructor
@RestController
public class PositionController {
    private final PositionRetriever retriever;

    public PositionController(PositionRetriever retriever) {
        this.retriever = retriever;
    }

    @GetMapping("/aircraft")
    public Iterable<Aircraft> getCurrentAircraftPositions() {
        return retriever.retrieveAircraftPositions();
    }

    @GetMapping("/aircraftadmin")
    public Iterable<Aircraft> getCurrentAircraftPositionsAdminPrivs() {
        return retriever.retrieveAircraftPositions();
    }
}