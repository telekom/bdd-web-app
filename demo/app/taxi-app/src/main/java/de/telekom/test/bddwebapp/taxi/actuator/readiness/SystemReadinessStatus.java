package de.telekom.test.bddwebapp.taxi.actuator.readiness;

import lombok.Data;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

@Data
public class SystemReadinessStatus {

    private final boolean essential;
    private Date lastSuccessfulRequest;
    private Date recovered;
    private Date firstFailedRequest;
    private Date lastFailedRequest;
    private String errorMessage;

    public SystemReadinessStatus(boolean essential) {
        this.essential = essential;
    }

    public boolean isReady() {
        return lastSuccessfulRequest != null && (lastFailedRequest == null || lastSuccessfulRequest.after(lastFailedRequest));
    }

    public void setSuccessfulState() {
        lastSuccessfulRequest = new Date();
        if (firstFailedRequest != null) {
            recovered = new Date();
        }
    }

    public boolean isSuccessfulRequestExpired(){
        return lastSuccessfulRequest == null || lastSuccessfulRequest.before(DateUtils.addMinutes(new Date(), -5));
    }

    public void setErrorState(String message) {
        if (firstFailedRequest == null || recovered != null) {
            firstFailedRequest = new Date();
            recovered = null;
        }
        lastFailedRequest = new Date();
        errorMessage = message;
    }
}
