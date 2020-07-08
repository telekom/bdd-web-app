package de.telekom.test.bddwebapp.testdata.builder;

import de.telekom.test.bddwebapp.testdata.controller.vo.RegistrationVO;
import de.telekom.test.bddwebapp.testdata.controller.vo.ReservationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2020 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
public class TestDataBuilder {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${taxi-app.url:http://localhost:5000/taxi-app}")
    private String taxiAppUrl;

    public RegistrationVO createNewUser() {
        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        var map = new LinkedMultiValueMap<String, String>();
        map.add("firstName", "Hans");
        map.add("lastName", "Müller");
        map.add("username", randomNumeric(8) + "@user.de");
        map.add("password", "password");
        var request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        var response = restTemplate.postForEntity(taxiAppUrl + "/registration", request, String.class);
        if (!response.getStatusCode().is3xxRedirection()) {
            throw new RuntimeException("Error creation user test data!");
        }

        var registration = new RegistrationVO();
        registration.setFirstName(map.getFirst("firstName"));
        registration.setLastName(map.getFirst("lastName"));
        registration.setUsername(map.getFirst("username"));
        registration.setPassword(map.getFirst("password"));
        return registration;
    }

    public ReservationVO createExampleReservation(String earliestStartTime, String latestStartTime) {
        var reservation = new ReservationVO();
        var tomorrow = new Date(new Date().getTime() + 86400000L);
        reservation.setDate(new SimpleDateFormat("dd.MM.yyyy").format(tomorrow));
        reservation.setDeparture("Alexanderplatz, Berlin");
        reservation.setDestination("Flughafen Berlin-Tegel");
        reservation.setEarliestStartTime(earliestStartTime);
        reservation.setLatestStartTime(latestStartTime);
        return reservation;
    }

}
