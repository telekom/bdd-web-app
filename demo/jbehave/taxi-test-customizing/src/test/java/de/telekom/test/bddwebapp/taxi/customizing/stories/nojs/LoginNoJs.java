package de.telekom.test.bddwebapp.taxi.customizing.stories.nojs;

import de.telekom.test.bddwebapp.jbehave.stories.config.AlternativeWebDriverConfiguration;
import de.telekom.test.bddwebapp.taxi.customizing.config.AbstractCustomizingStory;
import de.telekom.test.bddwebapp.taxi.customizing.config.webdriver.NoJsWebDriverConfiguration;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@AlternativeWebDriverConfiguration(NoJsWebDriverConfiguration.class)
public class LoginNoJs extends AbstractCustomizingStory {

}
