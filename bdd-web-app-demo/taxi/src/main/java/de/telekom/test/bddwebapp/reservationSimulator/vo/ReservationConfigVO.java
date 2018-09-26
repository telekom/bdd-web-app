package de.telekom.test.bddwebapp.reservationSimulator.vo;

import lombok.Data;

import java.util.List;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
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
