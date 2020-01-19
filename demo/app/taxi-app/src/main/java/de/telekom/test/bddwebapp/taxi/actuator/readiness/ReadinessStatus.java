package de.telekom.test.bddwebapp.taxi.actuator.readiness;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
@Slf4j
@Component
public class ReadinessStatus {

    private final Map<String, SystemReadinessStatus> systemStatusMap = new HashMap<>();

    public ReadinessStatus() {

    }

    public SystemReadinessStatus getStatus(String systemName) {
        SystemReadinessStatus systemReadinessStatus = systemStatusMap.get(systemName);
        if (systemReadinessStatus == null) {
            log.warn("Unknown status for system [{}]", systemName);
            systemReadinessStatus = new SystemReadinessStatus(true);
            systemStatusMap.put(systemName, systemReadinessStatus);
        }
        return systemReadinessStatus;
    }

    public boolean isReady() {
        return systemStatusMap
                .values()
                .stream()
                .filter(SystemReadinessStatus::isEssential)
                .allMatch(SystemReadinessStatus::isReady);
    }

}
