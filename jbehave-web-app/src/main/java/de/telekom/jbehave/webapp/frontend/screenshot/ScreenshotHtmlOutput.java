package de.telekom.jbehave.webapp.frontend.screenshot;

import org.apache.commons.lang3.StringUtils;
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
        if (screenshotsOnSuccess) {
            super.overwritePattern("successfulWithLink", "<div class=\"step successful\"><a target=\"_blank\" href=\"{1}\">{0}</a></div>");
        }
        super.overwritePattern("failed", "<div class=\"step failed\">{0}<span class=\"keyword failed\">({1})</span><br/><span class=\"message failed\">{2}</span><br/><a style=\"color: darkmagenta; font-weight: none; text-decoration: underline;\" target=\"_blank\" href=\"../screenshots/failed-scenario-{3}.png\">Screenshot</a></div>\n");
    }

    @Override
    public void beforeStory(Story story, boolean givenStory) {
        super.beforeStory(story, givenStory);
        currentStoryFolder = story.getName() + "_" + new Date().getTime();
    }

    @Override
    public void successful(String step) {
        if (screenshotsOnSuccess && step.contains("Then")) {
            String screenshotOnSuccessPath = screenshotMakerOnSuccess.makeScreenshot(currentStoryFolder, step);
            if (StringUtils.isNoneBlank(screenshotOnSuccessPath)) {
                this.print(this.format("successfulWithLink", "{0}", step, screenshotOnSuccessPath));
                return;
            }
        }
        super.successful(step);
    }

    @Override
    public void failed(String step, Throwable storyFailure) {
        super.failed(step, storyFailure);
        UUIDExceptionWrapper uuidWrappedFailure = (UUIDExceptionWrapper) storyFailure;
        screenshotMakerOnFailure.afterScenarioFailure(uuidWrappedFailure);
    }

}