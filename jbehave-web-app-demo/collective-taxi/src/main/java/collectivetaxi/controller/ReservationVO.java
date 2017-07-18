package collectivetaxi.controller;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class ReservationVO {

    @NotBlank
    private String departure;

    @NotBlank
    private String earliestStartTime;

    @NotBlank
    private String destination;

    @NotBlank
    private String latestStartTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReservationVO that = (ReservationVO) o;

        if (departure != null ? !departure.equals(that.departure) : that.departure != null) return false;
        if (earliestStartTime != null ? !earliestStartTime.equals(that.earliestStartTime) : that.earliestStartTime != null)
            return false;
        if (destination != null ? !destination.equals(that.destination) : that.destination != null) return false;
        return latestStartTime != null ? latestStartTime.equals(that.latestStartTime) : that.latestStartTime == null;
    }

}
