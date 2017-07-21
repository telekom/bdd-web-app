package org.jbehave.webapp.taxi.controller;

import org.jbehave.webapp.taxi.simulator.ReservationPriceVO;
import lombok.Data;

import java.util.List;

@Data
public class ReservationPricesVO {

    private String message;
    private List<ReservationPriceVO> reservationPrices;

}