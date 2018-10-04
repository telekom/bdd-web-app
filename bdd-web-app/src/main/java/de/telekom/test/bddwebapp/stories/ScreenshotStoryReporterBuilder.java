package de.telekom.test.bddwebapp.stories;

import de.telekom.test.bddwebapp.frontend.screenshot.ScreenshotReportForm;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.springframework.context.ApplicationContext;

public interface ScreenshotStoryReporterBuilder {

    default StoryReporterBuilder screenshotStoryReporterBuilder() {
        Format screenshotReportForm = getApplicationContext().getBean(ScreenshotReportForm.class);
        return new StoryReporterBuilder().withCrossReference(new CrossReference())
                .withFormats(Format.TXT, Format.CONSOLE, Format.STATS, Format.XML, screenshotReportForm)
                .withCodeLocation(CodeLocations.codeLocationFromClass(getClass())).withFailureTrace(true);
    }

    ApplicationContext getApplicationContext();

}
