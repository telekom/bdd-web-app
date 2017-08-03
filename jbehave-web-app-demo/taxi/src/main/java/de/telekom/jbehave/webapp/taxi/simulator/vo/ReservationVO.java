package de.telekom.jbehave.webapp.taxi.simulator.vo;

import lombok.Data;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
@Data
public class ReservationVO {

    private String date;
    private String departure;
    private String earliestStartTime;
    private String destination;
    private String latestStartTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservationVO that = (ReservationVO) o;

        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (departure != null ? !departure.equals(that.departure) : that.departure != null) return false;
        if (earliestStartTime != null ? !earliestStartTime.equals(that.earliestStartTime) : that.earliestStartTime != null)
            return false;
        if (destination != null ? !destination.equals(that.destination) : that.destination != null) return false;
        return latestStartTime != null ? latestStartTime.equals(that.latestStartTime) : that.latestStartTime == null;
    }

}
