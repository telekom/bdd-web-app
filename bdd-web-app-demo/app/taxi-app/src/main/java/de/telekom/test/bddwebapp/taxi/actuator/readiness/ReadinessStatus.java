package de.telekom.test.bddwebapp.taxi.actuator.readiness;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ReadinessStatus {

    @Getter
    private final Map<String, SystemReadinessStatus> systemStatusMap = new HashMap<>();

    public ReadinessStatus() {
        systemStatusMap.put("DB", new SystemReadinessStatus(true));
        systemStatusMap.put("TESTDATA-SIM", new SystemReadinessStatus(true));
    }

    public SystemReadinessStatus getStatus(String systemName) {
        var systemReadinessStatus = systemStatusMap.get(systemName);
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
