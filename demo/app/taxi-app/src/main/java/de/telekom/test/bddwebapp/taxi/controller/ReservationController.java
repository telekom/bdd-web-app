package de.telekom.test.bddwebapp.taxi.controller;

import de.telekom.test.bddwebapp.taxi.controller.validator.AuthenticationValidator;
import de.telekom.test.bddwebapp.taxi.controller.vo.ReservationPricesVO;
import de.telekom.test.bddwebapp.taxi.controller.vo.ReservationVO;
import de.telekom.test.bddwebapp.taxi.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.security.Principal;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Controller
public class ReservationController {

    @Autowired
    private AuthenticationValidator authenticationValidator;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${reservation-service.url:http://localhost:6000/testdata-sim/simulator/api}")
    private String reservationServiceUrl;

    @GetMapping("reservation")
    public String reservation(Principal principal, Model model) {
        if (authenticationValidator.isAuthenticated(principal, model)) {
            model.addAttribute("reservation", reservationService.getReservation(principal.getName()));
            return "reservation";
        }
        return "redirect:login";
    }

    @PostMapping("reservation")
    public @ResponseBody
    ReservationPricesVO reservation(Principal principal, @Valid @RequestBody ReservationVO reservation) {
        reservationService.saveReservation(principal.getName(), reservation);
        return restTemplate.postForObject(reservationServiceUrl + "/reservation", reservation, ReservationPricesVO.class);
    }

}
