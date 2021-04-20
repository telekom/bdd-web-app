package de.telekom.test.bddwebapp.stories.customizing;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Gives access to the current story about the Spring Context
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * @author Marc Eckart
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
public class CustomizingStories {

    @Getter
    private final List<String> restartBrowserBeforeScenarioThisFeatures = new ArrayList<>();

    @Getter
    @Setter
    private boolean restartBrowserBeforeScenarioForAllStories;

}
