package de.telekom.test.bddwebapp.testdata.builder;

import de.telekom.test.bddwebapp.testdata.controller.vo.RegistrationVO;
import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationEventVO;
import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationPriceEventVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
public class TestDataBuilder {

    private final WebClient webClient = WebClient.create();

    @Value("${taxi-app.url:http://localhost:5000/taxi-app}")
    private String taxiAppUrl;

    public RegistrationVO createNewUser() {
        var body = new LinkedMultiValueMap<String, String>();
        body.add("firstName", "Hans");
        body.add("lastName", "Müller");
        body.add("username", randomNumeric(8) + "@user.de");
        body.add("password", "password");

        webClient.post()
                .uri(taxiAppUrl + "/registration")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .body(BodyInserters.fromFormData(body))
                .retrieve()
                .bodyToMono(RegistrationVO.class)
                .onErrorMap(e -> new RuntimeException("Error creation user test data!"))
                .block();

        return new RegistrationVO(
                body.getFirst("firstName"), body.getFirst("lastName"),
                body.getFirst("username"), body.getFirst("password")
        );
    }

    public ReservationEventVO createExampleReservation(String earliestStartTime, String latestStartTime) {
        var reservation = new ReservationEventVO();
        var tomorrow = new Date(new Date().getTime() + 86400000L);
        reservation.setDate(new SimpleDateFormat("dd.MM.yyyy").format(tomorrow));
        reservation.setDeparture("Alexanderplatz, Berlin");
        reservation.setDestination("Flughafen Berlin-Tegel");
        reservation.setEarliestStartTime(earliestStartTime);
        reservation.setLatestStartTime(latestStartTime);
        reservation.setReservationPriceEvent(new ReservationPriceEventVO("Update reservation price event"));
        return reservation;
    }

}
