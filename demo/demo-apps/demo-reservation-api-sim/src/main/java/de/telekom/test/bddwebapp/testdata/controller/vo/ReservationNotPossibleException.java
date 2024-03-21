package de.telekom.test.bddwebapp.testdata.controller.vo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Reservation not possible!")
public class ReservationNotPossibleException extends RuntimeException {
}
