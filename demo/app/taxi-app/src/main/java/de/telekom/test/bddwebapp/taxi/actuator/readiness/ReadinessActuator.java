package de.telekom.test.bddwebapp.taxi.actuator.readiness;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.stereotype.Component;

import static org.springframework.boot.actuate.endpoint.web.WebEndpointResponse.STATUS_OK;
import static org.springframework.boot.actuate.endpoint.web.WebEndpointResponse.STATUS_SERVICE_UNAVAILABLE;

/**
 * This is a Service-Virtualization for a possible Reservation-API
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2021 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
@Endpoint(id = "readiness")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReadinessActuator {

    private final ReadinessStatus readinessStatus;

    @ReadOperation
    public WebEndpointResponse<ReadinessStatus> readiness() {
        return new WebEndpointResponse<>(readinessStatus, readinessStatus.isReady() ? STATUS_OK : STATUS_SERVICE_UNAVAILABLE);
    }

}
