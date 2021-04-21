package de.telekom.test.bddwebapp.taxi.actuator.readiness;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReadinessObserver {

    @Value("${testdata-sim.url}")
    private String testdataSim;

    private final WebClient webClient = WebClient.create();
    private final ReadinessStatus readinessStatus;
    private final JdbcTemplate jdbcTemplate;

    @Scheduled(fixedRate = 60 * 1000, initialDelay = 30 * 1000)
    public void observe() {
        log.info("[READINESS] Observer is checking if external services are ready");
        checkDbReadiness();
        checkTestdataSimReadiness();
        log.info("[READINESS] Observer finished checking external services");
    }

    private void checkDbReadiness() {
        var dbStatus = readinessStatus.getStatus("DB");
        try {
            log.info("[DB] Check database availability to check readiness");
            var results = jdbcTemplate.query("select 1", new SingleColumnRowMapper<>());
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
        var apiStatus = readinessStatus.getStatus("TESTDATA-SIM");
        if (!apiStatus.isReady() || apiStatus.isSuccessfulRequestExpired()) {
            log.info("[TESTDATA-SIM] is not ready. Calling health endpoint to check readiness");
            webClient.get()
                    .uri(testdataSim + "/actuator/health")
                    .retrieve()
                    .bodyToMono(Map.class)
                    .doOnSuccess(map -> apiStatus.setSuccessfulState())
                    .doOnError(ex -> apiStatus.setErrorState(ex.getMessage()))
                    .block();
        }
    }

}
