package de.telekom.jbehave.webapp.frontend.screenshot;

import org.jbehave.core.failures.UUIDExceptionWrapper;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.HtmlOutput;
import org.jbehave.core.reporters.StoryReporterBuilder;

import java.io.PrintStream;
import java.util.Date;

/**
 * Render screenshots for success and error at report
 *
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
public class ScreenshotHtmlOutput extends HtmlOutput {

    private final boolean screenshotsOnSuccess;
    private final ScreenshotOnFailure screenshotMakerOnFailure;
    private final ScreenshotOnSuccess screenshotMakerOnSuccess;

    private String currentStoryFolder;

    public ScreenshotHtmlOutput(PrintStream output,
                                StoryReporterBuilder reporterBuilder, boolean screenshotsOnSuccess, ScreenshotOnFailure screenshotMakerOnFailure, ScreenshotOnSuccess screenshotMakerOnSuccess) {
        super(output, reporterBuilder.keywords());
        this.screenshotsOnSuccess = screenshotsOnSuccess;
        this.screenshotMakerOnFailure = screenshotMakerOnFailure;
        this.screenshotMakerOnSuccess = screenshotMakerOnSuccess;
        changeALine();
    }

    private void changeALine() {
        super.overwritePattern("failed", "<div class=\"step failed\">{0} <span class=\"keyword failed\">({1})</span><br/><span class=\"message failed\">{2}</span><br/><a color=\"black\" target=\"jb_scn_shot\" href=\"../screenshots/failed-scenario-{3}.png\"><img src=\"images/failing_screenshot.png\" alt=\"failing screenshot\"/></a></div>\n");
    }

    @Override
    public void beforeStory(Story story, boolean givenStory) {
        super.beforeStory(story, givenStory);
        currentStoryFolder = story.getName() + "_" + new Date().getTime();
    }

    @Override
    public void successful(String step) {
        super.successful(step);
        if (screenshotsOnSuccess) {
            screenshotMakerOnSuccess.makeScreenshot(currentStoryFolder, step);
        }
    }

    @Override
    public void failed(String step, Throwable storyFailure) {
        super.failed(step, storyFailure);
        try {
            UUIDExceptionWrapper uuidWrappedFailure = (UUIDExceptionWrapper) storyFailure;
            screenshotMakerOnFailure.afterScenarioFailure(uuidWrappedFailure);
        } catch (Exception e) {
            System.out.println("Screenshot failed");
        }
    }

}