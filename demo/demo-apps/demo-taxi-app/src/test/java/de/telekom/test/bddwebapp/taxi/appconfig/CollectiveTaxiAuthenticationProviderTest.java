package de.telekom.test.bddwebapp.taxi.appconfig;

import de.telekom.test.bddwebapp.taxi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class CollectiveTaxiAuthenticationProviderTest {

    @InjectMocks
    private CollectiveTaxiAuthenticationProvider collectiveTaxiAuthenticationProvider;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void authenticateReturnsAuthenticatedTokenWhenCredentialsAreValid() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("username", "password");
        when(userService.isUsernameAndPasswordValid(anyString(), anyString())).thenReturn(true);

        Authentication result = collectiveTaxiAuthenticationProvider.authenticate(authentication);

        assertTrue(result.isAuthenticated());
    }

    @Test
    public void authenticateReturnsUnauthenticatedTokenWhenCredentialsAreInvalid() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("username", "password");
        when(userService.isUsernameAndPasswordValid(anyString(), anyString())).thenReturn(false);

        assertThrows(BadCredentialsException.class, () -> collectiveTaxiAuthenticationProvider.authenticate(authentication));
    }

    @Test
    public void authenticateReturnsUnauthenticatedTokenWhenCredentialsAreNull() {
        Authentication authentication = new UsernamePasswordAuthenticationToken("username", null);

        assertThrows(BadCredentialsException.class, () -> collectiveTaxiAuthenticationProvider.authenticate(authentication));
    }

    @Test
    public void supportsReturnsTrueForUsernamePasswordAuthenticationToken() {
        assertTrue(collectiveTaxiAuthenticationProvider.supports(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void supportsReturnsFalseForOtherAuthenticationTypes() {
        assertFalse(collectiveTaxiAuthenticationProvider.supports(Authentication.class));
    }

}