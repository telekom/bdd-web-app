package collectivetaxi.controller.validator;

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
		model.addAttribute("username", authenticationToken.getName());

		boolean authenticated = authenticationToken.isAuthenticated();
		model.addAttribute("isLoggedIn", authenticated);

		return authenticated;
	}

}
