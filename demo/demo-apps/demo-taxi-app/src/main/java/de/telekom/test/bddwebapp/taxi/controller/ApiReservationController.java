package de.telekom.test.bddwebapp.taxi.controller;

import de.telekom.test.bddwebapp.taxi.controller.vo.ReservationPriceEventVO;
import de.telekom.test.bddwebapp.taxi.controller.vo.ReservationVO;
import de.telekom.test.bddwebapp.taxi.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.security.Principal;

@RestController
@RequestMapping("api/reservations")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApiReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public Mono<ReservationPriceEventVO> createReservation(Principal principal, @RequestBody @Valid ReservationVO reservation) {
        assertUsername(principal, reservation);
        return reservationService.createReservation(principal.getName(), reservation);
    }

    private void assertUsername(Principal principal, ReservationVO reservation) {
        if (!principal.getName().equals(reservation.username())) {
            throw new AccessDeniedException("Username does not fit to principal!");
        }
    }

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ReservationPriceEventVO> getReservations(Principal principal) {
        return reservationService.getReservations(principal.getName());
    }

}
