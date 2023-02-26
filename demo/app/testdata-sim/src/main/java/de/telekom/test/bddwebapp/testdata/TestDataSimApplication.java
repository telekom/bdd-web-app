package de.telekom.test.bddwebapp.testdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@SpringBootApplication
public class TestDataSimApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TestDataSimApplication.class, args);
    }

}
