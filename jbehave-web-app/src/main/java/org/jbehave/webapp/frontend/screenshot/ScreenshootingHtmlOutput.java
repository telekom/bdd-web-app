package org.jbehave.webapp.frontend.screenshot;

import org.jbehave.core.failures.UUIDExceptionWrapper;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.HtmlOutput;
import org.jbehave.core.reporters.StoryReporterBuilder;

import java.io.PrintStream;
import java.util.Date;

public class ScreenshootingHtmlOutput extends HtmlOutput {

    private final boolean screenshotsOnSuccess;
    private final WebDriverScreenshotOnFailure screenshotMakerOnFailure;
    private final WebDriverScreenshotOnSuccess screenshotMakerOnSuccess;

    private String currentStoryFolder;

    public ScreenshootingHtmlOutput(PrintStream output,
                                    StoryReporterBuilder reporterBuilder, boolean screenshotsOnSuccess, WebDriverScreenshotOnFailure screenshotMakerOnFailure, WebDriverScreenshotOnSuccess screenshotMakerOnSuccess) {
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