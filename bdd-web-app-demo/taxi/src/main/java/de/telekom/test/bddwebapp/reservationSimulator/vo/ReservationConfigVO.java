package de.telekom.test.bddwebapp.reservationSimulator.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
@Data
public class ReservationConfigVO {

    private ReservationVO reservation;
    private List<ReservationPriceVO> reservationPrices;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservationConfigVO that = (ReservationConfigVO) o;

        return reservation != null ? reservation.equals(that.reservation) : that.reservation == null;
    }

}
