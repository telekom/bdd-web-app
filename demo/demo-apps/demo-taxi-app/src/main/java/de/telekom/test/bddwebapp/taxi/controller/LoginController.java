package de.telekom.test.bddwebapp.taxi.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model, HttpSession session) {
        model.addAttribute("registration", session.getAttribute("registration"));
        session.setAttribute("registration", null);
        model.addAttribute("username", session.getAttribute("username"));
        return "login";
    }

}