package de.telekom.test.bddwebapp.cucumber.extension;

import cucumber.api.event.*;
import de.telekom.test.bddwebapp.cucumber.hook.SpringConfigurationHook;
import de.telekom.test.bddwebapp.cucumber.hook.config.ContextConfig;
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
import java.util.Arrays;
import java.util.List;

import static de.telekom.test.bddwebapp.cucumber.extension.ApplicationContextReference.getApplicationContext;
import static de.telekom.test.bddwebapp.cucumber.extension.ApplicationContextReference.setApplicationContext;
import static de.telekom.test.bddwebapp.cucumber.extension.ExtendedLifeCycle.*;
import static java.util.Arrays.stream;
import static org.springframework.aop.support.AopUtils.getTargetClass;
import static org.springframework.aop.support.AopUtils.isAopProxy;

public class ExtendedLifeCyclePlugin implements ConcurrentEventListener {

    private EventHandler<TestRunFinished> testRunFinished = event -> {
       // TODO
        //  List<SpringConfigurationHook.StepMethodExecution> stepMethodExecutions = findByAnnotation(AfterAll.class);
        // executeStep(stepMethodExecutions);
    };

    @Override
    public void setEventPublisher(EventPublisher publisher) {
        publisher.registerHandlerFor(TestRunFinished.class, testRunFinished);
    }




}