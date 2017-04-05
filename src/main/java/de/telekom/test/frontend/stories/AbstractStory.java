package de.telekom.test.frontend.stories;

import com.google.common.collect.Lists;
import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;
import de.telekom.test.frontend.screenshot.ScreenshootingHtmlFormat;
import de.telekom.test.steps.Steps;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;

import java.util.List;

@RunWith(JUnitReportingRunner.class)
public abstract class AbstractStory extends JUnitStory {

	private Configuration createConfiguration() {
		Configuration configuration = new MostUsefulConfiguration();
		Format screenshootingHtmlFormat = getApplicationContext().getBean(ScreenshootingHtmlFormat.class);
		configuration.useStoryReporterBuilder(new StoryReporterBuilder().withCrossReference(new CrossReference())
				.withFormats(Format.TXT, Format.CONSOLE, Format.STATS, screenshootingHtmlFormat)
				.withCodeLocation(CodeLocations.codeLocationFromClass(getClass())).withFailureTrace(true));
		configuration.useStoryPathResolver(new UnderscoredCamelCaseResolver().removeFromClassName("Story"));
		return configuration;
	}

	@Override
	public Configuration configuration() {
		return createConfiguration();
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		List<Object> steps = Lists.newArrayList(getApplicationContext().getBeansWithAnnotation(Steps.class).values());
		return new InstanceStepsFactory(configuration(), steps);
	}

	public abstract ApplicationContext getApplicationContext();

}
