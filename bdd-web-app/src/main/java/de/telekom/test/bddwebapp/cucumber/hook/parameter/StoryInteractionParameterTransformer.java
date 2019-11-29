package de.telekom.test.bddwebapp.cucumber.hook.parameter;

import de.telekom.test.bddwebapp.cucumber.extension.ApplicationContextReference;
import de.telekom.test.bddwebapp.steps.InteractionParameterConverter;
import io.cucumber.cucumberexpressions.ParameterByTypeTransformer;
import io.cucumber.datatable.dependency.com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Type;

public class StoryInteractionParameterTransformer implements ParameterByTypeTransformer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public Object transform(String s, Type type) {
        ApplicationContext applicationContext = ApplicationContextReference.getApplicationContext();
        InteractionParameterConverter interactionParameterConverter = applicationContext.getBean(InteractionParameterConverter.class);
        Object valueFromKeyOrValueOrConcatenated = interactionParameterConverter.getValueFromKeyOrValueOrConcatenated(s);
        return objectMapper.convertValue(valueFromKeyOrValueOrConcatenated, objectMapper.constructType(type));
    }

}