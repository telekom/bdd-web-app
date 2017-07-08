package collectivetaxi.api;

import lombok.Data;

@Data
public class ReservationPriceVO {

    private String startTime;
    private String endTime;
    private String price;

}
