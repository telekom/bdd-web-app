package org.jbehave.webapp.taxi.simulator;

import lombok.Data;

@Data
public class ReservationPriceVO {

    private String startTime;
    private String endTime;
    private String price;

}
