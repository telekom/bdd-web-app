package de.telekom.test.bddwebapp.taxi;

import de.telekom.test.bddwebapp.taxi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
@Component
public class CollectiveTaxiAuthenticationProvider implements AuthenticationProvider {

    public final static SimpleGrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = getPassword(authentication);
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(ROLE_USER);
        UsernamePasswordAuthenticationToken passwordAuthenticationToken = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
        if (!isPasswordValid(name, password)) {
            passwordAuthenticationToken.setAuthenticated(false);
            passwordAuthenticationToken.setDetails("username_password_invalid");
        }
        return passwordAuthenticationToken;
    }

    private boolean isPasswordValid(String name, String password) {
        if (!StringUtils.hasText(password)) {
            return false;
        }
        return userService.isUsernameAndPasswordValid(name, password);
    }

    private String getPassword(Authentication authentication) {
        return authentication.getCredentials() != null ? authentication.getCredentials().toString() : null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
