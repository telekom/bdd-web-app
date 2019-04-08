package de.telekom.test.bddwebapp.testdata.testdata;

import de.telekom.test.bddwebapp.testdata.simulator.config.ReservationSimulatorConfig;
import de.telekom.test.bddwebapp.testdata.simulator.vo.ReservationPriceVO;
import de.telekom.test.bddwebapp.testdata.simulator.vo.ReservationVO;
import de.telekom.test.bddwebapp.testdata.testdata.vo.RegistrationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RestController("testdata")
public class TestDataController {

    @Autowired
    private ReservationSimulatorConfig reservationSimulatorConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${taxi-app.url:http://localhost:5000/taxi-app}")
    private String taxiAppUrl;

    @PostMapping("user")
    public RegistrationVO testDataUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("firstName", "Hans");
        map.add("lastName", "Müller");
        map.add("username", randomNumeric(8) + "@user.de");
        map.add("password", "password");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(taxiAppUrl + "/registration", request, String.class);
        if (!response.getStatusCode().is3xxRedirection()) {
            throw new RuntimeException("Error creation user test data!");
        }

        RegistrationVO registration = new RegistrationVO();
        registration.setFirstName(map.getFirst("firstName"));
        registration.setLastName(map.getFirst("lastName"));
        registration.setUsername(map.getFirst("username"));
        registration.setPassword(map.getFirst("password"));
        return registration;
    }

    @PutMapping("reservation")
    public void updatePossibleReservation(@RequestBody ReservationVO reservation) {
        reservationSimulatorConfig.setCurrentReservation(Optional.of(reservation));
    }

    @DeleteMapping("reservation")
    public void deletePossibleReservation() {
        reservationSimulatorConfig.setCurrentReservation(Optional.empty());
    }

    @PostMapping("prices")
    public List<ReservationPriceVO> addOfferedPrice(@RequestBody ReservationPriceVO reservationPrice) {
        ReservationVO reservation = reservationSimulatorConfig.getCurrentReservation()
                .orElseThrow(() -> new RuntimeException("No reservation available!"));
        reservation.getReservationPrices().add(reservationPrice);
        return reservation.getReservationPrices();
    }

}