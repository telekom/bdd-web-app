package de.telekom.test.bddwebapp.cucumber.hook;

import cucumber.api.event.TestCaseEvent;
import de.telekom.test.bddwebapp.cucumber.extension.BeforeAll;
import de.telekom.test.bddwebapp.cucumber.extension.BeforeFeature;
import de.telekom.test.bddwebapp.cucumber.extension.ExtendedLifeCyclePlugin;
import de.telekom.test.bddwebapp.cucumber.hook.config.ContextConfig;
import groovy.util.logging.Slf4j;
import io.cucumber.core.api.Scenario;
import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import io.cucumber.java.Before;
import lombok.AllArgsConstructor;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static de.telekom.test.bddwebapp.cucumber.extension.ApplicationContextReference.getApplicationContext;
import static de.telekom.test.bddwebapp.cucumber.extension.ApplicationContextReference.setApplicationContext;
import static de.telekom.test.bddwebapp.cucumber.extension.ExtendedLifeCycle.*;
import static de.telekom.test.bddwebapp.cucumber.extension.ExtendedLifeCycle.isBeforeFeature;
import static java.util.Arrays.stream;

/**
 * Class to use spring application context while running cucumber
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ContextConfig.class)
public class SpringConfigurationHook {

    private static final Logger LOG = LoggerFactory.getLogger(SpringConfigurationHook.class);

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Need this method so the cucumber will recognize this class as glue and load spring context configuration
     */
    @Before(order = 0)
    public void setUpSpringContext(Scenario scenario) {
        increaseTestCaseCountForBeforeAll();
        increaseTestCaseCountForFeature(scenario.getName());

        if (isBeforeAll()) {
            LOG.info("-------------- Spring Context Initialized For Executing Cucumber Tests --------------");
            setApplicationContext(applicationContext);

            List<StepMethodExecution> stepMethodExecutions = findByAnnotation(BeforeAll.class);
            executeStep(stepMethodExecutions);
        }
        if (isBeforeFeature()) {
            List<StepMethodExecution> stepMethodExecutions = findByAnnotation(BeforeFeature.class);
            executeStep(stepMethodExecutions);
        }
    }

    @AllArgsConstructor
    public class StepMethodExecution {
        private final Object object;
        private final Method method;
    }

    private void executeStep(List<StepMethodExecution> stepMethodExecutions) {
        stepMethodExecutions.forEach(stepMethodExecution -> {
            try {
                stepMethodExecution.method.invoke(stepMethodExecution.object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

    private List<StepMethodExecution> findByAnnotation(Class<? extends Annotation> annotationClass) {
        List<StepMethodExecution> stepMethodExecutions = new ArrayList<>();
        stream(applicationContext.getBeanDefinitionNames())
                .map(applicationContext::getBean).forEach(obj -> stream(obj.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotationClass))
                .map(method -> new StepMethodExecution(obj, method))
                .forEach(stepMethodExecutions::add));
        return stepMethodExecutions;
    }

}
