package de.telekom.test.bddwebapp.reservationSimulator;

import de.telekom.test.bddwebapp.reservationSimulator.vo.ReservationConfigVO;
import de.telekom.test.bddwebapp.reservationSimulator.vo.ReservationPricesVO;
import de.telekom.test.bddwebapp.reservationSimulator.vo.ReservationVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
@Service
public class ReservationSimulatorConfig {

    private final List<ReservationConfigVO> reservationConfigs = new ArrayList<>();

    public ReservationPricesVO reserve(ReservationVO reservation) {
        return reservationConfigs.stream()
                .filter(reservationConfig -> reservationConfig.getReservation().equals(reservation))
                .map(reservationConfig -> {
                    ReservationPricesVO reservationPrices = new ReservationPricesVO();
                    reservationPrices.setMessage("reservation possible");
                    reservationPrices.setReservationPrices(reservationConfig.getReservationPrices());
                    return reservationPrices;
                })
                .findFirst()
                .orElseGet(() -> {
                    ReservationPricesVO noReservationPossible = new ReservationPricesVO();
                    noReservationPossible.setMessage("no reservation possible");
                    return noReservationPossible;
                });
    }

    public void addOrUpdateReservationConfig(ReservationConfigVO reservationConfig) {
        reservationConfigs.remove(reservationConfig);
        reservationConfigs.add(reservationConfig);
    }

    public void clearReservationConfigs() {
        reservationConfigs.clear();
    }

}
