package de.telekom.test.frontend.screenshot;

import org.jbehave.core.failures.UUIDExceptionWrapper;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.web.selenium.WebDriverHtmlOutput;
import org.jbehave.web.selenium.WebDriverProvider;
import org.jbehave.web.selenium.WebDriverScreenshotOnFailure;

import java.io.PrintStream;
import java.util.Date;

public class ScreenshootingHtmlOutput extends WebDriverHtmlOutput {

	private boolean makeScreenshotIfSuccessful;
	private final WebDriverScreenshotOnFailure screenshotMakerOnFailure;
	private final WebDriverScreenshotOnSuccess screenshotMakerOnSuccess;
	private String currentStoryFolder;

	public ScreenshootingHtmlOutput(boolean makeScreenshotIfSuccessful, PrintStream output,
			StoryReporterBuilder reporterBuilder,
			WebDriverProvider webDriverProvider) {
		super(output, reporterBuilder.keywords());
		this.makeScreenshotIfSuccessful = makeScreenshotIfSuccessful;
		this.screenshotMakerOnFailure = new WebDriverScreenshotOnFailure(webDriverProvider);
		this.screenshotMakerOnSuccess = new WebDriverScreenshotOnSuccess(reporterBuilder, webDriverProvider);
	}

	@Override
	public void beforeStory(Story story, boolean givenStory) {
		super.beforeStory(story, givenStory);
		currentStoryFolder = story.getName() + "_" + new Date().getTime();
	}

	@Override
	public void successful(String step) {
		super.successful(step);
		if (makeScreenshotIfSuccessful) {
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

	public void setMakeScreenshotIfSuccessful(boolean makeScreenshotIfSuccessful) {
		this.makeScreenshotIfSuccessful = makeScreenshotIfSuccessful;
	}

}