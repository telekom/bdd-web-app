package de.telekom.test.bddwebapp.stories.config;

import de.telekom.test.bddwebapp.frontend.screenshot.ScreenshotReportForm;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.springframework.context.ApplicationContext;

/**
 * Enhanced the JBehave story report builder by screenshots.
 *
 * @author Daniel Keiss {@literal <daniel.keiss@telekom.de>}
 * <p>
 * Copyright (c) 2018 Daniel Keiss, Deutsche Telekom AG
 * This file is distributed under the conditions of the Apache License, Version 2.0.
 * For details see the file license on the toplevel.
 */
public interface ScreenshotStoryReporterBuilder {

    default StoryReporterBuilder screenshotStoryReporterBuilder() {
        Format screenshotReportForm = getApplicationContext().getBean(ScreenshotReportForm.class);
        return new StoryReporterBuilder().withCrossReference(new CrossReference())
                .withFormats(Format.TXT, Format.CONSOLE, Format.STATS, Format.XML, screenshotReportForm)
                .withCodeLocation(CodeLocations.codeLocationFromClass(getClass())).withFailureTrace(true);
    }

    ApplicationContext getApplicationContext();

}
