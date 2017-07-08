package collectivetaxi.api;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class ReservationVO {

    @NotBlank
    private String departure;

    @NotBlank
    private String startTime;

    @NotBlank
    private String destination;

    @NotBlank
    private String endTime;

}
