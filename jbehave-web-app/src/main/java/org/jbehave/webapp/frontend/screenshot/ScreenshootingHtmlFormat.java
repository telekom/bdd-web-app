package org.jbehave.webapp.frontend.screenshot;

import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.webapp.frontend.lifecycle.WebDriverWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ScreenshootingHtmlFormat extends Format {

    @Value("${screenshot.onsuccess:@null}")
    private String screenshotsOnSuccess;

    @Autowired
    private WebDriverWrapper webDriverWrapper;

    public ScreenshootingHtmlFormat() {
        super("HTML");
    }

    @Override
    public StoryReporter createStoryReporter(
            FilePrintStreamFactory factory,
            StoryReporterBuilder builder) {
        factory.useConfiguration(builder.fileConfiguration("html"));
        WebDriverScreenshotOnFailure screenshotMakerOnFailure = new WebDriverScreenshotOnFailure(builder, webDriverWrapper);
        WebDriverScreenshotOnSuccess screenshotMakerOnSuccess = new WebDriverScreenshotOnSuccess(builder, webDriverWrapper);
        ScreenshootingHtmlOutput screenshootingHtmlOutput = new ScreenshootingHtmlOutput(factory.createPrintStream(), builder, Boolean.valueOf(screenshotsOnSuccess), screenshotMakerOnFailure, screenshotMakerOnSuccess);
        return screenshootingHtmlOutput
                .doReportFailureTrace(builder.reportFailureTrace())
                .doCompressFailureTrace(builder.compressFailureTrace());
    }

}