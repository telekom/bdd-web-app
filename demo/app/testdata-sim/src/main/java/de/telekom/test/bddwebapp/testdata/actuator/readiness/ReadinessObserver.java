package de.telekom.test.bddwebapp.testdata.actuator.readiness;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ReadinessObserver {

    @Autowired
    private ReadinessStatus readinessStatus;

    @Scheduled(fixedRate = 120 * 1000, initialDelay = 60 * 1000)
    public void observe() {
        log.info("[READINESS] Observer is checking if external services are ready");
        log.info("[READINESS] Observer finished checking external services");
    }

}
