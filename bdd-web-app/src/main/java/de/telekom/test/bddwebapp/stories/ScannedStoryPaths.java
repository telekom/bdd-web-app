package de.telekom.test.bddwebapp.stories;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.io.StoryPathResolver;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface ScannedStoryPaths {

    default List<String> scannedStoryPaths() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(AbstractStory.class));
        Set<BeanDefinition> components = provider.findCandidateComponents(storiesBasePath());

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
    default String storiesBasePath() {
        return "";
    }

    Configuration configuration();

}
