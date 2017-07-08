package collectivetaxi.api;

import lombok.Data;

import java.util.List;

@Data
public class ReservationPricesVO {

    private String message;
    private List<ReservationPriceVO> reservationPrices;

}