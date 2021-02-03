package de.telekom.test.bddwebapp.testdata.config;

import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationEventVO;
import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationPriceEntryVO;
import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationPriceEventVO;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
public class ReservationSimulatorConfig {

    @Getter
    private Optional<ReservationEventVO> currentReservation = Optional.empty();

    public void setCurrentReservation(ReservationEventVO reservation) {
        currentReservation = Optional.of(reservation);
    }

    public void clearReservation() {
        currentReservation = Optional.empty();
    }

    public ReservationEventVO reserve(ReservationEventVO reservationEvent) {
        return currentReservation.orElseGet(() -> {
            reservationEvent.setReservationPriceEvent(new ReservationPriceEventVO("No offers available!"));
            return reservationEvent;
        });
    }

    public void updatePrice(ReservationPriceEntryVO priceEntry) {
        List<ReservationPriceEntryVO> reservationPrices = currentReservation.orElseThrow(() -> new RuntimeException("Please setup reservation test data!"))
                .getReservationPriceEvent().getReservationPrices();
        reservationPrices.removeIf(currentPrice -> currentPrice.getStartTime().equals(priceEntry.getStartTime()));
        reservationPrices.add(priceEntry);
        ReservationPriceEventVO reservationPriceEvent = new ReservationPriceEventVO("Update reservation price at "+new Date());
        reservationPriceEvent.getReservationPrices().addAll(reservationPrices);
        currentReservation.get().setReservationPriceEvent(reservationPriceEvent);
    }

}
