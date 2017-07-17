package simulator;

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
    public ResponseEntity<?> reservation(
            @Valid @RequestBody ReservationVO reservation, Errors errors) {

        if (errors.hasErrors()) {
            ReservationPricesVO reservationPrices = new ReservationPricesVO();
            reservationPrices.setMessage(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(reservationPrices);
        }

        ReservationPricesVO reservationPrices = reservationSimulatorConfig.reserve(reservation);
        return ok(reservationPrices);
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