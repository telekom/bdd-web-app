package de.telekom.jbehave.webapp.taxi.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class ReservationPricesVO {

    private String message;
    private List<ReservationPriceVO> reservationPrices;

}