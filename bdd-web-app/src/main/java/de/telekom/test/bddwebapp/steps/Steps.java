package de.telekom.test.bddwebapp.steps;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Regulating the lifecycle of the browser for JBehave frontend tests
 * <p>
 * After a first implementation of steps annotation by Sven Schomaker, working for Deutsche Telekom AG in 2013.
 * <p>
 *
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
@Component
public @interface Steps {

}
