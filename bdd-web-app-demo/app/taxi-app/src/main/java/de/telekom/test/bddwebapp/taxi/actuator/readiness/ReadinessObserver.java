package de.telekom.test.bddwebapp.taxi.actuator.readiness;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class ReadinessObserver {

    @Value("${testdata-sim.url:http://localhost:6000/testdata-sim}")
    private String testdataSim;

    @Autowired
    private ReadinessStatus readinessStatus;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(fixedRate = 60 * 1000, initialDelay = 30 * 1000)
    public void observe() {
        log.info("[READINESS] Observer is checking if external services are ready");
        checkDbReadiness();
        checkTestdataSimReadiness();
        log.info("[READINESS] Observer finished checking external services");
    }

    private void checkDbReadiness() {
        SystemReadinessStatus dbStatus = readinessStatus.getStatus("DB");
        try {
            log.info("[DB] Check database availability to check readiness");
            List<Object> results = jdbcTemplate.query("select 1", new SingleColumnRowMapper<>());
            if (!results.isEmpty()) {
                dbStatus.setSuccessfulState();
            } else {
                dbStatus.setErrorState("Database not available!");
            }
        } catch (DataAccessException ex) {
            dbStatus.setErrorState(ex.getMessage());
        }
    }

    private void checkTestdataSimReadiness() {
        SystemReadinessStatus apiStatus = readinessStatus.getStatus("TESTDATA-SIM");
        if (!apiStatus.isReady() || apiStatus.isSuccessfulRequestExpired()) {
            log.info("[TESTDATA-SIM] is not ready. Calling health endpoint to check readiness");
            try {
                restTemplate.getForObject(testdataSim + "/actuator/health", Map.class);
                apiStatus.setSuccessfulState();
            } catch (Exception ex) {
                apiStatus.setErrorState(ex.getMessage());
            }
        }
    }

}
