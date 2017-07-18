package collectivetaxi.simulator;

import lombok.Data;

import java.util.List;

@Data
public class ReservationConfigVO {

    private ReservationVO reservation;
    private List<ReservationPriceVO> reservationPrices;

}
