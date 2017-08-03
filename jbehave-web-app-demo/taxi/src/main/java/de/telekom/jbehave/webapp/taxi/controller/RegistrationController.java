package de.telekom.jbehave.webapp.taxi.controller;

import de.telekom.jbehave.webapp.taxi.controller.vo.RegistrationVO;
import de.telekom.jbehave.webapp.taxi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration() {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String addRegistration(RegistrationVO registration, HttpSession session) {
        userService.register(registration);
        session.setAttribute("registration", true);
        session.setAttribute("username", registration.getUsername());
        return "redirect:login";
    }

}
