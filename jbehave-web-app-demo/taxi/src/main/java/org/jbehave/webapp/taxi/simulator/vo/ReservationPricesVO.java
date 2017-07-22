package org.jbehave.webapp.taxi.simulator.vo;

import lombok.Data;

import java.util.List;

@Data
public class ReservationPricesVO {

    private String message;
    private List<ReservationPriceVO> reservationPrices;

}