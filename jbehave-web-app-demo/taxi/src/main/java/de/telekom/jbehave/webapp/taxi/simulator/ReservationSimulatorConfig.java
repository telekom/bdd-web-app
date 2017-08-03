package de.telekom.jbehave.webapp.taxi.simulator;

import de.telekom.jbehave.webapp.taxi.simulator.vo.ReservationConfigVO;
import de.telekom.jbehave.webapp.taxi.simulator.vo.ReservationVO;
import de.telekom.jbehave.webapp.taxi.simulator.vo.ReservationPricesVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationSimulatorConfig {

    private final List<ReservationConfigVO> reservationConfigs = new ArrayList<>();

    public ReservationPricesVO reserve(ReservationVO reservation) {

        for (ReservationConfigVO reservationConfig : reservationConfigs) {
            if (reservationConfig.getReservation().equals(reservation)) {
                ReservationPricesVO reservationPrices = new ReservationPricesVO();
                reservationPrices.setMessage("reservation possible");
                reservationPrices.setReservationPrices(reservationConfig.getReservationPrices());
                return reservationPrices;
            }
        }

        ReservationPricesVO noReservationPossible = new ReservationPricesVO();
        noReservationPossible.setMessage("no reservation possible");
        return noReservationPossible;
    }

    public void addOrUpdateReservationConfig(ReservationConfigVO reservationConfig) {
        reservationConfigs.remove(reservationConfig);
        reservationConfigs.add(reservationConfig);
    }

    public void clearReservationConfigs() {
        reservationConfigs.clear();
    }

}
