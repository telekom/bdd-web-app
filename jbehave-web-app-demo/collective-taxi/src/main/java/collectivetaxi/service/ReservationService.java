package collectivetaxi.service;

import collectivetaxi.api.ReservationPriceVO;
import collectivetaxi.api.ReservationPricesVO;
import collectivetaxi.api.ReservationVO;
import org.springframework.stereotype.Service;

import static java.util.Collections.singletonList;

@Service
public class ReservationService {

    public ReservationPricesVO reserve(ReservationVO reservation) {

        // fake it until you make it
        if (reservation.getDeparture().equals("OrtA") && reservation.getDestination().equals("OrtB") && reservation.getStartTime().equals("10:00") && reservation.getEndTime().equals("12:00")) {
            ReservationPricesVO reservationPrices = new ReservationPricesVO();
            reservationPrices.setMessage("reservation possible");
            ReservationPriceVO reservationPrice = new ReservationPriceVO();
            reservationPrice.setPrice("15,50");
            reservationPrice.setStartTime("10:00");
            reservationPrice.setEndTime("11:00");
            reservationPrices.setReservationPrices(singletonList(reservationPrice));
            return reservationPrices;
        }

        ReservationPricesVO noReservationPossible = new ReservationPricesVO();
        noReservationPossible.setMessage("no reservation possible");
        return noReservationPossible;
    }

}
