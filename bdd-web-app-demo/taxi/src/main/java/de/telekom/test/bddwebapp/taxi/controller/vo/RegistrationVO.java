package de.telekom.test.bddwebapp.taxi.controller.vo;

import lombok.Data;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
@Data
public class RegistrationVO {

    private String firstName;
    private String lastName;
    private String username;
    private String password;

}
