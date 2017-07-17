package simulator;

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
        if (!super.equals(o)) return false;

        ReservationVO that = (ReservationVO) o;

        if (!departure.equals(that.departure)) return false;
        if (!earliestStartTime.equals(that.earliestStartTime)) return false;
        if (!destination.equals(that.destination)) return false;
        return latestStartTime.equals(that.latestStartTime);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + departure.hashCode();
        result = 31 * result + earliestStartTime.hashCode();
        result = 31 * result + destination.hashCode();
        result = 31 * result + latestStartTime.hashCode();
        return result;
    }
}
