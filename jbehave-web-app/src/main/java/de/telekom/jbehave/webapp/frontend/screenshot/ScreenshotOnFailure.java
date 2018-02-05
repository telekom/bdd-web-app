package de.telekom.jbehave.webapp.frontend.screenshot;

import de.telekom.jbehave.webapp.frontend.lifecycle.WebDriverWrapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.core.annotations.ScenarioType;
import org.jbehave.core.failures.PendingStepFound;
import org.jbehave.core.failures.UUIDExceptionWrapper;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

import static java.text.MessageFormat.format;

/**
 * Add failure screenshot to report
 *
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
@AllArgsConstructor
public class ScreenshotOnFailure {

    private final static Logger LOGGER = LoggerFactory.getLogger(ScreenshotOnFailure.class);

    private final StoryReporterBuilder reporterBuilder;
    private final WebDriverWrapper webDriverWrapper;

    @AfterScenario(uponType = ScenarioType.EXAMPLE, uponOutcome = Outcome.FAILURE)
    public void afterScenarioWithExamplesFailure(UUIDExceptionWrapper uuidWrappedFailure) {
        afterScenarioFailure(uuidWrappedFailure);
    }

    @AfterScenario(uponType = ScenarioType.NORMAL, uponOutcome = Outcome.FAILURE)
    public void afterScenarioFailure(UUIDExceptionWrapper uuidWrappedFailure) {
        if (uuidWrappedFailure instanceof PendingStepFound) {
            return;
        }
        String screenshotPath = screenshotPath(uuidWrappedFailure.getUUID());
        String currentUrl = "[unknown page title]";
        try {
            currentUrl = webDriverWrapper.getDriver().getCurrentUrl();
        } catch (Exception ignored) {
        }
        try {
            screenshotPath = webDriverWrapper.saveScreenshotTo(screenshotPath);
        } catch (Exception e) {
            LOGGER.error(format("Screenshot of page '{0}' failed", currentUrl), e);
        }
        if (StringUtils.isNoneBlank(screenshotPath)) {
            LOGGER.info(format("Screenshot of page '{0}' has been saved to '{1}'", currentUrl, screenshotPath));
        } else {
            LOGGER.error(format("Screenshot of page '{0}' has **NOT** been saved'", currentUrl));
        }
    }

    private String screenshotPath(UUID uuid) {
        return format("{0}/screenshots/failed-scenario-{1}.png", reporterBuilder.outputDirectory(), uuid);
    }

}