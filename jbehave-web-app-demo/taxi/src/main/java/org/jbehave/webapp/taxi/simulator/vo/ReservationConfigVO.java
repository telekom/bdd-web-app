package org.jbehave.webapp.taxi.simulator.vo;

import lombok.Data;

import java.util.List;

@Data
public class ReservationConfigVO {

    private ReservationVO reservation;
    private List<ReservationPriceVO> reservationPrices;

}
