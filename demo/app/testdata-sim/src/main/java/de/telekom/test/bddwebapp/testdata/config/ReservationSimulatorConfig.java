package de.telekom.test.bddwebapp.testdata.config;

import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationPriceVO;
import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationVO;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
public class ReservationSimulatorConfig {

    private Optional<ReservationVO> currentReservation;

    public void setCurrentReservation(ReservationVO reservation) {
        currentReservation = Optional.of(reservation);
    }

    public void clearReservation() {
        currentReservation = Optional.empty();
    }

    public ReservationVO getCurrentReservation() {
        return currentReservation.orElseThrow(() -> new RuntimeException("Please setup reservation test data!"));
    }

    public ReservationVO reserve(ReservationVO reservation) {
        return currentReservation.orElseGet(() -> {
            reservation.setMessage("No offers available!");
            return reservation;
        });
    }

    public void updatePrice(ReservationPriceVO givenPrice) {
        List<ReservationPriceVO> reservationPrices = getCurrentReservation().getReservationPrices();
        reservationPrices.removeIf(currentPrice -> currentPrice.getStartTime().equals(givenPrice.getStartTime()));
        reservationPrices.add(givenPrice);
    }
}
