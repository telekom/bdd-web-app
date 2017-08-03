package de.telekom.jbehave.webapp.taxi.controller.validator;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.security.Principal;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
@Component
public class AuthenticationValidator {

    public boolean isAuthenticated(Principal principal, Model model) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        if (authenticationToken == null || !StringUtils.hasText(authenticationToken.getName())) {
            return false;
        }
        boolean authenticated = authenticationToken.isAuthenticated();
        if (!authenticated) {
            model.addAttribute("usernamePasswordInvalid", authenticationToken.getDetails() != null && authenticationToken.getDetails().toString().contains("username_password_invalid"));
        }
        return authenticated;
    }

}
