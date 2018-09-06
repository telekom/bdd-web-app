package de.telekom.jbehave.webapp.frontend.screenshot;

import de.telekom.jbehave.webapp.frontend.lifecycle.WebDriverWrapper;
import org.jbehave.core.reporters.FilePrintStreamFactory;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporter;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Screenshot report form
 *
 * @author Daniel Keiss
 * <p>
 * Copyright (c) 2017 Daniel Keiss, Deutsche Telekom AG
 */
@Component
public class ScreenshotReportForm extends Format {

    @Value("${screenshot.onsuccess:@null}")
    private String screenshotsOnSuccess;

    @Autowired
    private WebDriverWrapper webDriverWrapper;

    public ScreenshotReportForm() {
        super("HTML");
    }

    @Override
    public StoryReporter createStoryReporter(
            FilePrintStreamFactory factory,
            StoryReporterBuilder builder) {
        factory.useConfiguration(builder.fileConfiguration("html"));
        ScreenshotOnFailure screenshotMakerOnFailure = new ScreenshotOnFailure(builder, webDriverWrapper);
        ScreenshotOnSuccess screenshotMakerOnSuccess = new ScreenshotOnSuccess(builder, webDriverWrapper);
        ScreenshotHtmlOutput screenshotHtmlOutput = new ScreenshotHtmlOutput(factory.createPrintStream(), builder, Boolean.valueOf(screenshotsOnSuccess), screenshotMakerOnFailure, screenshotMakerOnSuccess);
        return screenshotHtmlOutput.doReportFailureTrace(builder.reportFailureTrace()).doCompressFailureTrace(builder.compressFailureTrace());
    }

}