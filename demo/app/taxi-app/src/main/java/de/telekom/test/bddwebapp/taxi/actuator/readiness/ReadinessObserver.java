package de.telekom.test.bddwebapp.taxi.actuator.readiness;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ReadinessObserver {

    @Autowired
    private ReadinessStatus readinessStatus;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Scheduled(fixedRate = 120 * 1000, initialDelay = 60 * 1000)
    public void observe() {
        log.info("[READINESS] Observer is checking if external services are ready");
        checkDbReadiness();
        log.info("[READINESS] Observer finished checking external services");
    }

    private void checkDbReadiness() {
        SystemReadinessStatus dbStatus = readinessStatus.getStatus("DB");
        try {
            log.info("[DB] Check database availability to check readiness");
            List<Object> results = jdbcTemplate.query("select 1 from dual", new SingleColumnRowMapper<>());
            if (!results.isEmpty()) {
                dbStatus.setSuccessfulState();
            } else {
                dbStatus.setErrorState("Database not available!");
            }
        } catch (DataAccessException ex) {
            dbStatus.setErrorState(ex.getMessage());
        }
    }

}
