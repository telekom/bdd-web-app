package de.telekom.test.bddwebapp.jbehave.steps;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Restart the Browser before scenario interaction.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Target(value = ElementType.TYPE)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface RestartBrowserBeforeScenario {
}
