package de.telekom.test.bddwebapp.taxi.controller;

import de.telekom.test.bddwebapp.taxi.controller.vo.ReservationPriceEventVO;
import de.telekom.test.bddwebapp.taxi.controller.vo.ReservationVO;
import de.telekom.test.bddwebapp.taxi.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api")
public class ApiReservationController {

    private final WebClient webClient = WebClient.create();

    @Value("${reservation-service.url:http://localhost:6000/testdata-sim/api}")
    private String reservationServiceUrl;

    @Autowired
    private ReservationService reservationService;

    @PostMapping("reservation")
    public Flux<ReservationPriceEventVO> reservation(Principal principal, @RequestBody @Valid ReservationVO reservation) {
        reservationService.saveReservation(principal.getName(), reservation);
        reservation.setUsername(principal.getName());
        return webClient.post()
                .uri(reservationServiceUrl + "/reservation")
                .body(BodyInserters.fromValue(reservation))
                .retrieve()
                .bodyToFlux(ReservationPriceEventVO.class);
    }

}
