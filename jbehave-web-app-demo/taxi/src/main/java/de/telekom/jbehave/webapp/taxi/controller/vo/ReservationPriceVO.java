package de.telekom.jbehave.webapp.taxi.controller.vo;

import lombok.Data;

@Data
public class ReservationPriceVO {

    private String startTime;
    private String endTime;
    private String price;
    private String passengers;

}
