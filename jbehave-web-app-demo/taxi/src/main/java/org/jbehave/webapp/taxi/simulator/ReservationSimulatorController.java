package org.jbehave.webapp.taxi.simulator;

import org.jbehave.webapp.taxi.simulator.vo.ReservationConfigVO;
import org.jbehave.webapp.taxi.simulator.vo.ReservationPricesVO;
import org.jbehave.webapp.taxi.simulator.vo.ReservationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class ReservationSimulatorController {

    @Autowired
    private ReservationSimulatorConfig reservationSimulatorConfig;

    @PostMapping("/simulator/api/reservation")
    public ReservationPricesVO reservation(
            @Valid @RequestBody ReservationVO reservation) {
        ReservationPricesVO reservationPrices = reservationSimulatorConfig.reserve(reservation);
        return reservationPrices;
    }

    @PutMapping("/simulator/config/reservation")
    public void reservationPrice(
            @Valid @RequestBody ReservationConfigVO reservationConfig) {
        reservationSimulatorConfig.addOrUpdateReservationConfig(reservationConfig);
    }

    @DeleteMapping("/simulator/config/reservation")
    public void reservationPrice() {
        reservationSimulatorConfig.clearReservationConfigs();
    }

}