package org.jbehave.webapp.stories;

import com.google.common.collect.Lists;
import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;
import org.jbehave.webapp.frontend.screenshot.ScreenshootingHtmlFormat;
import org.jbehave.webapp.steps.Steps;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.CodeLocations;
import org.jbehave.core.io.StoryPathResolver;
import org.jbehave.core.io.UnderscoredCamelCaseResolver;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RunWith(JUnitReportingRunner.class)
public abstract class RunAllStories extends JUnitStories {

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

    @Override
    public List<String> storyPaths() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(AbstractStory.class));
        Set<BeanDefinition> components = provider.findCandidateComponents("");

        List<String> storyPaths = new ArrayList<>();
        for (BeanDefinition component : components) {
            try {
                Class cls = Class.forName(component.getBeanClassName());
                StoryPathResolver pathResolver = configuration().storyPathResolver();
                String storyPath = pathResolver.resolve(cls);
                storyPaths.add(storyPath);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return storyPaths;
    }

    /*
     * Override for better scan performance
     */
    public String storiesBasePath() {
        return "";
    }

    public abstract ApplicationContext getApplicationContext();

}
