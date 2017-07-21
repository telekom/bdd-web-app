package org.jbehave.webapp.frontend.screenshot;

import org.jbehave.webapp.frontend.lifecycle.WebDriverWrapper;
import lombok.AllArgsConstructor;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.core.annotations.ScenarioType;
import org.jbehave.core.failures.PendingStepFound;
import org.jbehave.core.failures.UUIDExceptionWrapper;
import org.jbehave.core.reporters.StoryReporterBuilder;

import java.io.File;
import java.text.MessageFormat;
import java.util.UUID;

@AllArgsConstructor
public class WebDriverScreenshotOnFailure {

    private final StoryReporterBuilder reporterBuilder;
    private final WebDriverWrapper webDriverWrapper;

    @AfterScenario(uponType = ScenarioType.EXAMPLE, uponOutcome = Outcome.FAILURE)
    public void afterScenarioWithExamplesFailure(UUIDExceptionWrapper uuidWrappedFailure) throws Exception {
        afterScenarioFailure(uuidWrappedFailure);
    }

    @AfterScenario(uponType = ScenarioType.NORMAL, uponOutcome = Outcome.FAILURE)
    public void afterScenarioFailure(UUIDExceptionWrapper uuidWrappedFailure) throws Exception {
        if (uuidWrappedFailure instanceof PendingStepFound) {
            return;
        }
        String screenshotPath = screenshotPath(uuidWrappedFailure.getUUID());
        String currentUrl = "[unknown page title]";
        try {
            currentUrl = webDriverWrapper.getDriver().getCurrentUrl();
        } catch (Exception e) {
        }
        boolean savedIt = false;
        try {
            savedIt = webDriverWrapper.saveScreenshotTo(screenshotPath);
        } catch (Exception e) {
            System.out.println("Screenshot of page '" + currentUrl + ". Cause: " + e.getMessage());
        }
        if (savedIt) {
            System.out.println("Screenshot of page '" + currentUrl + "' has been saved to '" + screenshotPath + "' with " + new File(screenshotPath).length() + " bytes");
        } else {
            System.err.println("Screenshot of page '" + currentUrl + "' has **NOT** been saved. If there is no error, perhaps the WebDriver type you are using is not compatible with taking screenshots");
        }
    }

    private String screenshotPath(UUID uuid) {
        return MessageFormat.format("{0}/screenshots/failed-scenario-{1}.png", reporterBuilder.outputDirectory(), uuid);
    }

}