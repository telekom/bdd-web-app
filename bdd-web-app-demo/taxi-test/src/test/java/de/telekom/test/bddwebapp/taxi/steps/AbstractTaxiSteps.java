package de.telekom.test.bddwebapp.taxi.steps;

import de.telekom.test.bddwebapp.api.RequestBuilder;
import de.telekom.test.bddwebapp.frontend.steps.SeleniumSteps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public abstract class AbstractTaxiSteps extends SeleniumSteps {

    @Value("${hostIncludingPort}")
    protected String hostIncludingPort;

    @Value("${contextPath:}")
    protected String contextPath;

    @Value("${proxyHost:}")
    protected String proxyHost;

    @Value("${proxyPort:}")
    protected String proxyPort;

    @Autowired
    protected RequestBuilder requestBuilder;

    protected RequestBuilder request() {
        return requestBuilder.requestWithJsonConfig(hostIncludingPort, contextPath, proxyHost, proxyPort);
    }

}
