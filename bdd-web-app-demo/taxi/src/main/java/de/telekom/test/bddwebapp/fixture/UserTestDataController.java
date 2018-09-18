package de.telekom.test.bddwebapp.fixture;

import de.telekom.test.bddwebapp.taxi.controller.vo.RegistrationVO;
import de.telekom.test.bddwebapp.taxi.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
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
        registration.setUsername(RandomStringUtils.randomNumeric(8) + "@user.de");
        registration.setPassword("password");

        userService.register(registration);

        return registration;
    }

}