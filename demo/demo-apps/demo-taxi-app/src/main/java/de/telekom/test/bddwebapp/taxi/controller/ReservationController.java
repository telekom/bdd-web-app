package de.telekom.test.bddwebapp.taxi.controller;


import de.telekom.test.bddwebapp.taxi.controller.validator.AuthenticationValidator;
import de.telekom.test.bddwebapp.taxi.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReservationController {

    private final AuthenticationValidator authenticationValidator;
    private final ReservationService reservationService;

    @GetMapping("reservation")
    public String reservation(Principal principal, Model model) {
        if (authenticationValidator.isAuthenticated(principal, model)) {
            reservationService.getReservation(principal.getName()).ifPresent(reservation ->
                    model.addAttribute("reservation", reservation));
            return "reservation";
        }
        return "redirect:login";
    }

}
