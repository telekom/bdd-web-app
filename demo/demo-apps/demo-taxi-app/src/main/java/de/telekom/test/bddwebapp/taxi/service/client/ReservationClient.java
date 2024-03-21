package de.telekom.test.bddwebapp.taxi.service.client;

import de.telekom.test.bddwebapp.taxi.controller.vo.ReservationEventVO;
import de.telekom.test.bddwebapp.taxi.controller.vo.ReservationPriceEventVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ReservationClient {

    @Value("${reservation-service.url}")
    private String reservationServiceUrl;
    private WebClient webClient = WebClient.create();

    public Mono<ReservationPriceEventVO> createReservation(ReservationEventVO reservation) {
        return webClient.post()
                .uri(reservationServiceUrl + "/api/reservations")
                .body(BodyInserters.fromValue(reservation))
                .retrieve()
                .bodyToMono(ReservationPriceEventVO.class);
    }

    public Flux<ReservationPriceEventVO> getReservations(Long userId) {
        return webClient.get()
                .uri(reservationServiceUrl + "/api/reservation/" + userId)
                .retrieve()
                .bodyToFlux(ReservationPriceEventVO.class);
    }

}
