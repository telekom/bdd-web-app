package de.telekom.test.bddwebapp.jbehave.report;

import de.telekom.test.bddwebapp.frontend.lifecycle.WebDriverWrapper;
import de.telekom.test.bddwebapp.jbehave.stories.customizing.CurrentStory;
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
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2023 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
@Component
public class ScreenshotReportForm extends Format {

    @Value("${screenshot.onsuccess:true}")
    private String screenshotsOnSuccess;

    @Autowired
    private WebDriverWrapper webDriverWrapper;

    @Autowired
    private CurrentStory currentStory;

    public ScreenshotReportForm() {
        super("HTML");
    }

    @Override
    public StoryReporter createStoryReporter(FilePrintStreamFactory factory, StoryReporterBuilder builder) {
        factory.useConfiguration(builder.fileConfiguration("html"));
        var screenshotCreator = new ScreenshotCreator(builder, webDriverWrapper);
        var screenshotHtmlOutput = new ScreenshotHtmlOutput(factory.createPrintStream(), builder,
                Boolean.parseBoolean(screenshotsOnSuccess), screenshotCreator, currentStory);
        return screenshotHtmlOutput.doReportFailureTrace(builder.reportFailureTrace()).doCompressFailureTrace(builder.compressFailureTrace());
    }

}