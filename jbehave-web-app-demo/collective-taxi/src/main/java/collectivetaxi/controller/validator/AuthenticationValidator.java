package collectivetaxi.controller.validator;

import collectivetaxi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;

import java.security.Principal;

@Component
public class AuthenticationValidator {

    public boolean isAuthenticated(Principal principal) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) principal;
        if (authenticationToken == null || !StringUtils.hasText(authenticationToken.getName())) {
            return false;
        }
        return authenticationToken.isAuthenticated();
    }

}
