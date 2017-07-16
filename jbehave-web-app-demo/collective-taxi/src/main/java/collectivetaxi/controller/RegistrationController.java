package collectivetaxi.controller;

import collectivetaxi.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Principal principal, Model model) {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String addRegistration(RegistrationVO registration, HttpSession session) {
        registrationService.register(registration);
        session.setAttribute("registration", true);
        session.setAttribute("username", registration.getUsername());
        return "redirect:login";
    }

}
