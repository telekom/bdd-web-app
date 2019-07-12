package de.telekom.test.bddwebapp.frontend.screenshot;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jbehave.core.failures.PendingStepFound;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.HtmlOutput;
import org.jbehave.core.reporters.StoryReporterBuilder;

import java.io.PrintStream;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.isNoneBlank;

/**
 * Render screenshots for success and error at report
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2019 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public class ScreenshotHtmlOutput extends HtmlOutput {

    protected final boolean screenshotsOnSuccess;
    protected final ScreenshotCreator screenshotCreator;

    protected String currentStoryFolder;

    public ScreenshotHtmlOutput(PrintStream output,
                                StoryReporterBuilder reporterBuilder, boolean screenshotsOnSuccess, ScreenshotCreator screenshotCreator) {
        super(output, reporterBuilder.keywords());
        this.screenshotsOnSuccess = screenshotsOnSuccess;
        this.screenshotCreator = screenshotCreator;
        successScreenshotPattern();
        failedScreenshotPattern();
    }

    protected void successScreenshotPattern() {
        if (screenshotsOnSuccess) {
            super.overwritePattern("successfulWithScreenshot", "<div class=\"step successful\"><a target=\"_blank\" href=\"{0}\">{1}</a></div>\n");
        }
    }

    protected void failedScreenshotPattern() {
        super.overwritePattern("failedWithScreenshot", "<div class=\"step failed\"><a style=\"color: darkmagenta; font-weight: none; text-decoration: underline;\" target=\"_blank\" href=\"{3}\">{0}</a> <span class=\"keyword failed\">({1})</span><br/><span class=\"message failed\">{2}</span></div>");
    }

    @Override
    public void beforeStory(Story story, boolean givenStory) {
        super.beforeStory(story, givenStory);
        currentStoryFolder = story.getName() + "_" + new Date().getTime();
    }

    @Override
    public void successful(String step) {
        if (!screenshotsOnSuccess || !step.contains("Then") || !successfulScreenshot(step)) {
            super.successful(step);
        }
    }

    protected boolean successfulScreenshot(String step) {
        String screenshotPath = screenshotCreator.createScreenshot(currentStoryFolder, step, "successful");
        if (isNoneBlank(screenshotPath)) {
            this.print(this.format("successfulWithScreenshot", "{0} {1}\n", screenshotPath, step));
            return true;
        }
        return false;
    }

    @Override
    public void failed(String step, Throwable storyFailure) {
        if (storyFailure instanceof PendingStepFound || !failedScreenshot(step, storyFailure)) {
            super.failed(step, storyFailure);
        }
    }

    protected boolean failedScreenshot(String step, Throwable storyFailure) {
        String screenshotPath = screenshotCreator.createScreenshot(currentStoryFolder, step, "failed");
        if (isNoneBlank(screenshotPath)) {
            String stackTrace = ExceptionUtils.getStackTrace(storyFailure.getCause());
            this.print(this.format("failedWithScreenshot", "{3} {0} ({1})\n({2})\n", step, "FAILED", stackTrace, screenshotPath));
            return true;
        }
        return false;
    }

}