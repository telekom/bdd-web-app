package collectivetaxi.simulator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class ReservationSimulatorController {

    @Autowired
    private ReservationSimulatorConfig reservationSimulatorConfig;

    @PostMapping("/api/reservation")
    public ReservationPricesVO reservation(
            @Valid @RequestBody ReservationVO reservation) {
        ReservationPricesVO reservationPrices = reservationSimulatorConfig.reserve(reservation);
        return reservationPrices;
    }

    @PostMapping("/config/reservation")
    public ReservationConfigVO reservationPrice(
            @Valid @RequestBody ReservationConfigVO reservationConfig) {
        reservationSimulatorConfig.addReservationConfig(reservationConfig);
        return reservationConfig;
    }

    @DeleteMapping("/config/reservation")
    public void reservationPrice() {
        reservationSimulatorConfig.clearReservationConfigs();
    }

}