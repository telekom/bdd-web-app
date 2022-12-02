package de.telekom.test.bddwebapp.taxi.controller;

import de.telekom.test.bddwebapp.taxi.controller.vo.ReservationPriceEventVO;
import de.telekom.test.bddwebapp.taxi.controller.vo.ReservationVO;
import de.telekom.test.bddwebapp.taxi.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import jakarta.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApiReservationController {

    @Value("${reservation-service.url}")
    private String reservationServiceUrl;

    private final WebClient webClient = WebClient.create();
    private final ReservationService reservationService;

        @PostMapping("reservations")
    public Mono<ReservationPriceEventVO> createReservation(Principal principal, @RequestBody @Valid ReservationVO reservation) {
        reservationService.saveReservation(principal.getName(), reservation);
        reservation.setUsername(principal.getName());
        return webClient.post()
                .uri(reservationServiceUrl)
                .body(BodyInserters.fromValue(reservation))
                .retrieve()
                .bodyToMono(ReservationPriceEventVO.class);
    }

    @GetMapping(path = "reservations", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ReservationPriceEventVO> getReservations(Principal principal) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(reservationServiceUrl + "/{username}")
                        .build(principal.getName()))
                .retrieve()
                .bodyToFlux(ReservationPriceEventVO.class);
    }

}
