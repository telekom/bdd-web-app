package de.telekom.jbehave.webapp.taxi.controller;

import de.telekom.jbehave.webapp.taxi.controller.validator.AuthenticationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class IndexController {

    @Autowired
    private AuthenticationValidator authenticationValidator;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String root(Principal principal, Model model) {
        if (authenticationValidator.isAuthenticated(principal, model)) {
            return "redirect:reservation";
        }
        return "redirect:login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Principal principal, Model model, HttpSession session) {
        if (authenticationValidator.isAuthenticated(principal, model)) {
            return "redirect:reservation";
        }
        model.addAttribute("registration", session.getAttribute("registration"));
        session.setAttribute("registration", null);
        model.addAttribute("username", session.getAttribute("username"));
        return "login";
    }

}