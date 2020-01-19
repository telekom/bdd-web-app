package de.telekom.test.bddwebapp.testdata.actuator.readiness;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.web.WebEndpointResponse;
import org.springframework.stereotype.Component;

import static org.springframework.boot.actuate.endpoint.web.WebEndpointResponse.STATUS_OK;
import static org.springframework.boot.actuate.endpoint.web.WebEndpointResponse.STATUS_SERVICE_UNAVAILABLE;

@Component
@Endpoint(id = "readiness")
public class ReadinessActuator {

    @Autowired
    private ReadinessStatus readinessStatus;

    @ReadOperation
    public WebEndpointResponse<ReadinessStatus> readiness() {
        return new WebEndpointResponse<>(readinessStatus, readinessStatus.isReady() ? STATUS_OK : STATUS_SERVICE_UNAVAILABLE);
    }

}
