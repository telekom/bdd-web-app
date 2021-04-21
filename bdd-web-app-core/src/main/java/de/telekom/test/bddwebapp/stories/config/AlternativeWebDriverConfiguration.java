package de.telekom.test.bddwebapp.stories.config;

import de.telekom.test.bddwebapp.frontend.lifecycle.UsefulWebDriverConfiguration;
import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverConfiguration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Override the default web driver configuration by a own for a specific story.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface AlternativeWebDriverConfiguration {

    Class<? extends WebDriverConfiguration> value() default UsefulWebDriverConfiguration.class;

}
