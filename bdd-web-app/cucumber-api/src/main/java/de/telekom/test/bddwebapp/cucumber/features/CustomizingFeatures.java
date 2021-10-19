package de.telekom.test.bddwebapp.cucumber.features;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Gives access to the current story about the Spring Context
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * @author Marc Eckart
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
public class CustomizingFeatures {

    @Getter
    private final List<String> restartBrowserBeforeScenarioThisFeatures = new ArrayList<>();

    @Getter
    @Setter
    private boolean restartBrowserBeforeScenarioForAllStories;

}
