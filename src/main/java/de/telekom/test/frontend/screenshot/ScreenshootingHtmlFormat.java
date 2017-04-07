package de.telekom.test.frontend.screenshot;

import de.telekom.test.frontend.lifecycle.WebDriverWrapper;
import lombok.NonNull;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ScreenshootingHtmlFormat extends Format {

	private final @NonNull WebDriverWrapper webDriverWrapper;

	@Value("${yeti.systest.screenshot.onsuccess}")
	private boolean screenshotsOnSuccess;

	public ScreenshootingHtmlFormat(@Autowired WebDriverWrapper webDriverWrapper) {
		super("HTML");
		this.webDriverWrapper = webDriverWrapper;
	}

	@Override
	public StoryReporter createStoryReporter(
			FilePrintStreamFactory factory,
			StoryReporterBuilder builder) {
		factory.useConfiguration(
				builder.fileConfiguration("html"));
		ScreenshootingHtmlOutput screenshootingHtmlOutput =
				new ScreenshootingHtmlOutput(screenshotsOnSuccess, factory.createPrintStream(), builder, webDriverWrapper);
		return screenshootingHtmlOutput
				.doReportFailureTrace(builder.reportFailureTrace())
				.doCompressFailureTrace(builder.compressFailureTrace());
	}

}