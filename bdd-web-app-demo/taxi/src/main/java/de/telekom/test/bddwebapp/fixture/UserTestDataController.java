package de.telekom.test.bddwebapp.fixture;

import de.telekom.test.bddwebapp.taxi.controller.vo.RegistrationVO;
import de.telekom.test.bddwebapp.taxi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@RestController
public class UserTestDataController {

    @Autowired
    private UserService userService;

    @PostMapping("/testData/user")
    public RegistrationVO testDataUser() {
        RegistrationVO registration = new RegistrationVO();
        registration.setFirstName("Hans");
        registration.setLastName("Müller");
        registration.setUsername(randomNumeric(8) + "@user.de");
        registration.setPassword("password");

        userService.register(registration);

        return registration;
    }

}