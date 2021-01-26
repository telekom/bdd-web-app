package de.telekom.test.bddwebapp.testdata.config;

import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationEventVO;
import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationPriceEventVO;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.List.of;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2020 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
public class ReservationSimulatorConfig {

    private Optional<ReservationEventVO> currentReservation;

    public void setCurrentReservation(ReservationEventVO reservation) {
        currentReservation = Optional.of(reservation);
    }

    public void clearReservation() {
        currentReservation = Optional.empty();
    }

    public ReservationEventVO getCurrentReservation() {
        return currentReservation.orElseThrow(() -> new RuntimeException("Please setup reservation test data!"));
    }

    public ReservationEventVO reserve(ReservationEventVO reservationEvent) {
        return currentReservation.orElseGet(() -> {
            ReservationPriceEventVO reservationPrice = new ReservationPriceEventVO();
            reservationPrice.setMessage("No offers available!");
            reservationEvent.setReservationPrices(of(reservationPrice));
            return reservationEvent;
        });
    }

    public void updatePrice(ReservationPriceEventVO reservationPriceEvent) {
        var reservationPrices = getCurrentReservation().getReservationPrices();
        reservationPrices.removeIf(currentPrice -> currentPrice.getStartTime().equals(reservationPriceEvent.getStartTime()));
        reservationPrices.add(reservationPriceEvent);
    }

}
