package de.telekom.test.bddwebapp.frontend.steps


import spock.lang.Specification

/**
 * Unit test
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
class SeleniumStepsUrlAppenderTest extends Specification {

    def seleniumSteps = new SeleniumSteps() {
    }

    void setup() {
    }

    def "get url with host with host and path"() {
        when:
        def url = seleniumSteps.getUrlWithHost(hostIncludingPort, path)
        then:
        url == "http:localhost:8080/path"
        where:
        hostIncludingPort      | path
        "http:localhost:8080"  | "path"
        "http:localhost:8080/" | "/path"
        "http:localhost:8080/" | "path"
        "http:localhost:8080"  | "/path"
    }

    def "get url with host with host, context-path and path"() {
        when:
        def url = seleniumSteps.getUrlWithHost(hostIncludingPort, context, path)
        then:
        url == "http:localhost:8080/context/path"
        where:
        hostIncludingPort      | context     | path
        "http:localhost:8080"  | "context"   | "path"
        "http:localhost:8080/" | "context"   | "/path"
        "http:localhost:8080/" | "context"   | "path"
        "http:localhost:8080"  | "context"   | "/path"
        "http:localhost:8080"  | "context/"  | "path"
        "http:localhost:8080/" | "context/"  | "/path"
        "http:localhost:8080/" | "context/"  | "path"
        "http:localhost:8080"  | "context/"  | "/path"
        "http:localhost:8080"  | "/context/" | "path"
        "http:localhost:8080/" | "/context/" | "/path"
        "http:localhost:8080/" | "/context/" | "path"
        "http:localhost:8080"  | "/context/" | "/path"
    }

    def "get url with host with host, context-path, path and query params"() {
        when:
        def url = seleniumSteps.getUrlWithHost("http:localhost:8080", "context", "path", ["key": "value"])
        then:
        url == "http:localhost:8080/context/path?key=value"
    }

}
