package de.telekom.test.bddwebapp.taxi.customizing.steps;

import de.telekom.test.bddwebapp.steps.Steps;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;

import static org.junit.Assert.fail;

@Steps
@Slf4j
public class BeforeInTestLevel0 {

    @Before
    public void before() {
        if (Integer.parseInt(System.getProperty("testLevel")) > 0) {
            fail("This step should be overridden by BeforeInTestLevel1");
        }
    }

}
