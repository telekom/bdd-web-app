package de.telekom.test.bddwebapp.taxi.controller.validator;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.security.Principal;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
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
