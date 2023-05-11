package de.telekom.test.bddwebapp.jbehave.stories.config;

import de.telekom.test.bddwebapp.jbehave.report.ScreenshotReportForm;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.springframework.context.ApplicationContext;

/**
 * Enhanced the JBehave story report builder by screenshots.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2022 Daniel Keiss, Deutsche Telekom IT GmbH
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public interface ScreenshotStoryReporterBuilder {

    default StoryReporterBuilder screenshotStoryReporterBuilder() {
        var screenshotReportForm = getApplicationContext().getBean(ScreenshotReportForm.class);
        return new StoryReporterBuilder()
                .withFormats(Format.STATS, screenshotReportForm)
                .withCodeLocation(CodeLocations.codeLocationFromClass(getClass()))
                .withFailureTrace(true);
    }

    ApplicationContext getApplicationContext();

}
