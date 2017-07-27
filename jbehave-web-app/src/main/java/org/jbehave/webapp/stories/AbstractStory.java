package org.jbehave.webapp.stories;

import com.google.common.collect.Lists;
import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryPathResolver;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.jbehave.webapp.frontend.screenshot.ScreenshotReportForm;
import org.jbehave.webapp.steps.Steps;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;

import java.util.List;

@RunWith(JUnitReportingRunner.class)
public abstract class AbstractStory extends JUnitStory {

    public Configuration defaultConfiguration() {
        Configuration configuration = new MostUsefulConfiguration();
        configuration.useStoryReporterBuilder(defaultStoryReporterBuilder());
        configuration.useStoryPathResolver(defaultStoryPathResolver());
        return configuration;
    }

    public StoryReporterBuilder defaultStoryReporterBuilder() {
        Format screenshootingHtmlFormat = getApplicationContext().getBean(ScreenshotReportForm.class);
        return new StoryReporterBuilder().withCrossReference(new CrossReference())
                .withFormats(Format.TXT, Format.CONSOLE, Format.STATS, screenshootingHtmlFormat)
                .withCodeLocation(CodeLocations.codeLocationFromClass(getClass())).withFailureTrace(true);
    }

    public StoryPathResolver defaultStoryPathResolver() {
        return new UnderscoredCamelCaseResolver().removeFromClassName("Story");
    }

    @Override
    public Configuration configuration() {
        return defaultConfiguration();
    }

    @Override
    public InjectableStepsFactory stepsFactory() {
        List<Object> steps = Lists.newArrayList(getApplicationContext().getBeansWithAnnotation(Steps.class).values());
        return new InstanceStepsFactory(configuration(), steps);
    }

    public abstract ApplicationContext getApplicationContext();

}
