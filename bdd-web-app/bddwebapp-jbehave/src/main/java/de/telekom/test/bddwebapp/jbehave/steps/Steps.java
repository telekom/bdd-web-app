package de.telekom.test.bddwebapp.jbehave.steps;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Regulating the lifecycle of the browser for JBehave frontend tests
 * <p>
 * After a first implementation of steps annotation by Sven Schomaker, working for Deutsche Telekom AG in 2013.
 * <p>
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
@Component
public @interface Steps {

    int testLevel() default 0;

}
