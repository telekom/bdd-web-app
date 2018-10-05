package de.telekom.test.bddwebapp.stories.config;

import com.github.valfirst.jbehave.junit.monitoring.JUnitReportingRunner;
import org.junit.runner.RunWith;

import java.lang.annotation.Inherited;

@RunWith(JUnitReportingRunner.class)
@Inherited
public @interface StoryRunner {

    int[] testLevels() default 0;

}
