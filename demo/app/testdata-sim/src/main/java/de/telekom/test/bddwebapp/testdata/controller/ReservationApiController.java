package de.telekom.test.bddwebapp.testdata.controller;

import de.telekom.test.bddwebapp.testdata.config.ReservationSimulatorConfig;
import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationEventVO;
import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationPriceEventVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.Duration;
import java.util.function.Function;

/**
 * This is a Service-Virtualization for a possible Reservation-API
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RestController
@RequestMapping("api")
public class ReservationApiController {

    private final ReservationSimulatorConfig reservationSimulatorConfig;

    public ReservationApiController(ReservationSimulatorConfig reservationSimulatorConfig) {
        this.reservationSimulatorConfig = reservationSimulatorConfig;
    }

    @PostMapping(path = "reservations")
    public Mono<ReservationPriceEventVO> createReservation(@Valid ReservationEventVO reservation) {
        ReservationEventVO reserve = reservationSimulatorConfig.reserve(reservation);
        return Mono.just(reserve.getReservationPriceEvent());
    }

    @GetMapping(path = "reservations/{username}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ReservationPriceEventVO> getReservations(@PathVariable String username) {
        return Flux.interval(Duration.ofSeconds(1))
                .map(currentReservationPriceEvent())
                .distinctUntilChanged();
    }

    private Function<Long, ReservationPriceEventVO> currentReservationPriceEvent() {
        return duration -> reservationSimulatorConfig.getCurrentReservation()
                .map(ReservationEventVO::getReservationPriceEvent)
                .orElse(ReservationPriceEventVO.NO_PRICES_YET);
    }

}