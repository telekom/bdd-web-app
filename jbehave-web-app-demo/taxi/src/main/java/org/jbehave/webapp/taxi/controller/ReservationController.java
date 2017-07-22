package org.jbehave.webapp.taxi.controller;

import org.jbehave.webapp.taxi.controller.validator.AuthenticationValidator;
import org.jbehave.webapp.taxi.controller.vo.ReservationPricesVO;
import org.jbehave.webapp.taxi.controller.vo.ReservationVO;
import org.jbehave.webapp.taxi.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class ReservationController {

    @Autowired
    private AuthenticationValidator authenticationValidator;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private RestTemplate restTemplate;

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
    ReservationPricesVO reservation(Principal principal, HttpServletRequest request,
                                    @Valid @RequestBody ReservationVO reservation) {
        reservationService.saveReservation(principal.getName(), reservation);
        // call reservation simulator
        String reservationSimulatorUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/simulator/api/reservation";
        ReservationPricesVO reservationPrices = restTemplate.postForObject(reservationSimulatorUrl, reservation, ReservationPricesVO.class);
        return reservationPrices;
    }

}
