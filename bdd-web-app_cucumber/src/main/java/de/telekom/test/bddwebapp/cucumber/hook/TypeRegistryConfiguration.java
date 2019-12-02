package de.telekom.test.bddwebapp.cucumber.hook;

import de.telekom.test.bddwebapp.cucumber.hook.parameter.StoryInteractionParameterTransformer;
import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;

import java.util.Locale;

import static java.util.Locale.ENGLISH;

public class TypeRegistryConfiguration implements TypeRegistryConfigurer {

    public void configureTypeRegistry(TypeRegistry registry) {
        StoryInteractionParameterTransformer jacksonTableTransformer = new StoryInteractionParameterTransformer();
        registry.setDefaultParameterTransformer(jacksonTableTransformer);
    }

    @Override
    public Locale locale() {
        return ENGLISH;
    }

}
