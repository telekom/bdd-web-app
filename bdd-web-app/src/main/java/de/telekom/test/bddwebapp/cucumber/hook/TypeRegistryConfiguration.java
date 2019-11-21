package de.telekom.test.bddwebapp.cucumber.hook;

import de.telekom.test.bddwebapp.steps.StoryInteractionParameterConverter;
import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import io.cucumber.cucumberexpressions.ParameterType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

import static java.util.Locale.ENGLISH;

public class TypeRegistryConfiguration implements TypeRegistryConfigurer {

    @Autowired
    private StoryInteractionParameterConverter storyInteractionParameterConverter;

    @Override
    public Locale locale() {
        return ENGLISH;
    }

    @Override
    public void configureTypeRegistry(TypeRegistry typeRegistry) {
        typeRegistry.defineParameterType(new ParameterType<>(
                "param",
                "/\\w/",
                String.class,
                (String s) -> storyInteractionParameterConverter.getValueFromKeyOrValueOrConcatenated(s))
        );
    }
}
