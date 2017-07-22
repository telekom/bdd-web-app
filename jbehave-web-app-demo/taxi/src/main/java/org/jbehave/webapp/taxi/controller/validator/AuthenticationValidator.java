package org.jbehave.webapp.taxi.controller.validator;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.security.Principal;

@Component
public class AuthenticationValidator {

    public boolean isAuthenticated(Principal principal, Model model) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        if (authenticationToken == null || !StringUtils.hasText(authenticationToken.getName())) {
            return false;
        }
        boolean authenticated = authenticationToken.isAuthenticated();
        if(!authenticated){
            model.addAttribute("usernamePasswordInvalid", true);
        }
        return authenticated;
    }

}
